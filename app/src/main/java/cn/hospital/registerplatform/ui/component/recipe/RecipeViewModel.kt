package cn.hospital.registerplatform.ui.component.recipe

import androidx.lifecycle.asLiveData
import cn.hospital.registerplatform.data.repository.RecipeRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel
@Inject constructor(private val recipeRepository: RecipeRepository) : BaseViewModel() {
    fun getRecipeInfo(recipeId: Int) = recipeRepository.getRecipeInfo(recipeId)
    fun getExamInfo(examId: Int) = recipeRepository.getExamInfo(examId)
    fun getPrescriptionInfo(prescriptionId: Int) = recipeRepository.getPrescriptionInfo(prescriptionId)

    fun getDetailInfoList(examIds: List<Int>, prescriptionIds: List<Int>) =
        recipeRepository.getDetailInfoList(examIds, prescriptionIds).asLiveData()

    fun getRecipeList() = recipeRepository.getRecipeList()
}
