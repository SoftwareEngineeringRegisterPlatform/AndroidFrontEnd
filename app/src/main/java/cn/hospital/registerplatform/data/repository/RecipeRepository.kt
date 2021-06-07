package cn.hospital.registerplatform.data.repository

import androidx.paging.PagingConfig
import cn.hospital.registerplatform.api.interfaces.RecipeApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.ExamInfo
import cn.hospital.registerplatform.data.dto.PrescriptionInfo
import cn.hospital.registerplatform.data.dto.RecipeInfo
import cn.hospital.registerplatform.data.dto.suspendFunctionToFlow
import cn.hospital.registerplatform.data.pagingsource.getRawResultList

class RecipeRepository(
    private val recipeApi: RecipeApi,
    private val userPreference: UserPreference,
    private val pagingConfig: PagingConfig
) {
    fun getRecipeList() = getRawResultList(pagingConfig) { loadType, page, size ->
        recipeApi.getRecipeList(userPreference.getCachedToken(), loadType, page, size)
    }

    fun getRecipeInfo(recipeId: Int) = suspendFunctionToFlow<RecipeInfo> {
        recipeApi.getRecipeInfo(userPreference.getCachedToken(), recipeId)
    }

    fun getExamInfo(resultId: Int) = suspendFunctionToFlow<ExamInfo> {
        recipeApi.getExamInfo(userPreference.getCachedToken(), resultId)
    }

    fun getPrescriptionInfo(prescriptionId: Int) = suspendFunctionToFlow<PrescriptionInfo> {
        recipeApi.getPrescriptionInfo(userPreference.getCachedToken(), prescriptionId)
    }
}
