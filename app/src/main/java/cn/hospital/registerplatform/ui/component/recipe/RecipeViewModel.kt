package cn.hospital.registerplatform.ui.component.recipe

import android.util.Log
import androidx.lifecycle.asLiveData
import cn.hospital.registerplatform.data.dto.LoadType
import cn.hospital.registerplatform.data.repository.RecipeRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import cn.hospital.registerplatform.data.dto.RecipeInfoEdit
import cn.hospital.registerplatform.data.dto.RecipeInfoSubmit

@HiltViewModel
class RecipeViewModel
@Inject constructor(private val recipeRepository: RecipeRepository) : BaseViewModel() {
    fun getRecipeInfo(recipeId: Int) = recipeRepository.getRecipeInfo(recipeId)
    fun getExamInfo(examId: Int) = recipeRepository.getExamInfo(examId)
    fun getPrescriptionInfo(prescriptionId: Int) = recipeRepository.getPrescriptionInfo(prescriptionId)

    fun getDetailInfoList(examIds: List<Int>, prescriptionIds: List<Int>) =
        recipeRepository.getDetailInfoList(examIds, prescriptionIds).asLiveData()

    fun getDoctorRecipeList() = recipeRepository.getDoctorRecipeList(LoadType.ALL).asLiveData()

    fun getRecipeList() = recipeRepository.getRecipeList()
    fun editRecipeInfo(recipeId: Int, newRecipeDiag: String, newRecipeSuggestion: String) =
        recipeRepository.editRecipeInfo(
            recipeId,
            RecipeInfoEdit(newRecipeDiag, newRecipeSuggestion)
        ).asLiveData()

    fun submitRecipeInfo(user: Int, regist: Int, newRecipeDiag: String, newRecipeSuggestion: String) =
        recipeRepository.submitRecipeInfo(
            RecipeInfoSubmit(user, regist, newRecipeDiag, newRecipeSuggestion)
        ).asLiveData()
}
