package cn.hospital.registerplatform.ui.component.recipe

import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.PrescriptionInfo
import cn.hospital.registerplatform.data.dto.RecipeDetailCombinedListItem
import cn.hospital.registerplatform.data.repository.RecipeRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel
@Inject constructor(private val recipeRepository: RecipeRepository) : BaseViewModel() {
    fun getRecipeInfo(recipeId: Int) = recipeRepository.getRecipeInfo(recipeId)
    fun getExamInfo(examId: Int) = recipeRepository.getExamInfo(examId)
    fun getPrescriptionInfo(prescriptionId: Int) = recipeRepository.getPrescriptionInfo(prescriptionId)

    suspend fun getDetailInfoList(examIds: List<Int>, prescriptionIds: List<Int>): MutableList<RecipeDetailCombinedListItem> {
        val resultList : MutableList<RecipeDetailCombinedListItem> = mutableListOf()
        examIds.map {
            getExamInfo(it)
        }.forEach { it ->
            it.collect { resExamInfo ->
                resExamInfo.doSuccess { examInfo ->
                    resultList.add(RecipeDetailCombinedListItem(resultList.size - 1, examInfo.date, true, examInfo, null))
                }
            }
        }
        prescriptionIds.map {
            getPrescriptionInfo(it)
        }.forEach { it ->
            it.collect { resPrescriptionInfo ->
                resPrescriptionInfo.doSuccess { prescriptionInfo ->
                    resultList.add(RecipeDetailCombinedListItem(resultList.size - 1, prescriptionInfo.date, false, null, prescriptionInfo))
                }
            }
        }

        resultList.sortWith(Comparator {a: RecipeDetailCombinedListItem, b: RecipeDetailCombinedListItem ->
            (a.date.getTime() - b.date.getTime()).toInt()
        })
        return resultList
    }
    fun getRecipeList() = recipeRepository.getRecipeList()
}