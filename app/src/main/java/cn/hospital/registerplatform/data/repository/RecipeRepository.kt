package cn.hospital.registerplatform.data.repository

import androidx.paging.PagingConfig
import cn.hospital.registerplatform.api.Resource
import cn.hospital.registerplatform.api.interfaces.HospitalApi
import cn.hospital.registerplatform.api.interfaces.RecipeApi
import cn.hospital.registerplatform.api.interfaces.RegisterApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.*
import cn.hospital.registerplatform.data.pagingsource.getList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RecipeRepository(
    private val recipeApi: RecipeApi,
    private val registerApi: RegisterApi,
    private val hospitalApi: HospitalApi,
    private val userPreference: UserPreference,
    private val pagingConfig: PagingConfig
) {
    fun getRecipeList() = getList(pagingConfig) { loadType, page, size ->
        val rawResult = recipeApi.getRecipeList(userPreference.getCachedToken(), loadType, page, size)
        val rawRegisterResult = registerApi.getRegisterList(userPreference.getCachedToken(), loadType, page, size)
        val rawFinishedRegister = rawResult.content.map {
            rawRegisterResult.content.filter { reg -> it.regist == reg.id }[0]
        }
//            rawRegisterResult.content.filter { reg -> rawResult.content.any { res -> res.regist == reg.id } }
        if (rawResult.success) {
            rawResult.content.mapIndexed { index, it ->
                RecipeCombinedListItem(
                    it.id, it, recipeApi.getRecipeInfo(userPreference.getCachedToken(), it.regist).content,
                    hospitalApi.getDoctorInfo(rawFinishedRegister[index].doctorId).content
                )
            }
        } else {
            listOf()
        }
    }

    fun getDoctorRecipeList() = getList(pagingConfig) { loadType, page, size ->
        val rawResult = recipeApi.getDoctorRecipeList(userPreference.getCachedToken(), loadType)
        val rawRegisterResult = registerApi.getRegisterList(userPreference.getCachedToken(), loadType, page, size)
        val rawFinishedRegister = rawResult.content.map {
            rawRegisterResult.content.filter { reg -> it.regist == reg.id }[0]
        }
//            rawRegisterResult.content.filter { reg -> rawResult.content.any { res -> res.regist == reg.id } }
        if (rawResult.success) {
            rawResult.content.mapIndexed { index, it ->
                RecipeCombinedListItem(
                    it.id, it, recipeApi.getRecipeInfo(userPreference.getCachedToken(), it.regist).content,
                    hospitalApi.getDoctorInfo(rawFinishedRegister[index].doctorId).content
                )
            }
        } else {
            listOf()
        }
    }

    fun getRecipeInfo(recipeId: Int) = suspendFunctionToFlow<RecipeInfo> {
        recipeApi.getRecipeInfo(userPreference.getCachedToken(), recipeId)
    }

    fun submitRecipeInfo(recipeInfo: RecipeInfoSubmit) = suspendFunctionToFlow<String> {
        recipeApi.submitRecipe(userPreference.getCachedToken(), recipeInfo)
    }

    fun editRecipeInfo(recipeId: Int, recipeInfo: RecipeInfoEdit) = suspendFunctionToFlow<String> {
        recipeApi.editRecipe(userPreference.getCachedToken(), recipeId, recipeInfo)
    }

    fun getExamInfo(resultId: Int) = suspendFunctionToFlow<ExamInfo> {
        recipeApi.getExamInfo(userPreference.getCachedToken(), resultId)
    }

    fun submitExamInfo(examInfo: ExamInfoSubmit) = suspendFunctionToFlow<String> {
        recipeApi.submitExam(userPreference.getCachedToken(), examInfo)
    }

    fun editExamInfo(examId: Int, examInfo: ExamInfoEdit) = suspendFunctionToFlow<String> {
        recipeApi.editExam(userPreference.getCachedToken(), examId, examInfo)
    }

    fun getPrescriptionInfo(prescriptionId: Int) = suspendFunctionToFlow<PrescriptionInfo> {
        recipeApi.getPrescriptionInfo(userPreference.getCachedToken(), prescriptionId)
    }

    fun submitPrescriptionInfo(prescriptionInfo: PrescriptionInfoSubmit) = suspendFunctionToFlow<String> {
        recipeApi.submitPrescription(userPreference.getCachedToken(), prescriptionInfo)
    }

    fun editPrescriptionInfo(prescriptionId: Int, prescriptionInfo: PrescriptionInfoEdit) = suspendFunctionToFlow<String> {
        recipeApi.editPrescription(userPreference.getCachedToken(), prescriptionId, prescriptionInfo)
    }

    fun getDetailInfoList(examIds: List<Int>, prescriptionIds: List<Int>) =
        flow {
            val resultList = examIds.map {
                getExamInfo(it).first()
            }.filterIsInstance<Resource.Success<ExamInfo>>()
                .mapIndexed { index, resource ->
                    RecipeDetailCombinedListItem(index, resource.value.date, true, resource.value, null)
                }.toMutableList()
            resultList.addAll(prescriptionIds.map {
                getPrescriptionInfo(it).first()
            }.filterIsInstance<Resource.Success<PrescriptionInfo>>().mapIndexed { index, resource ->
                RecipeDetailCombinedListItem(
                    resultList.size + index,
                    resource.value.date,
                    false,
                    null,
                    resource.value
                )
            })
            resultList.sortWith { a: RecipeDetailCombinedListItem, b: RecipeDetailCombinedListItem ->
                (a.date.time - b.date.time).toInt()
            }
            emit(resultList)
        }
}
