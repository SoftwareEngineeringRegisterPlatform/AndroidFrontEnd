package cn.hospital.registerplatform.api.interfaces

import cn.hospital.registerplatform.data.dto.*
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("Recipe/RecipeList")
    suspend fun getRecipeList(
        @Query("token") token: String,
        @Query("loadType") LoadType: LoadType,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RawResult<List<RecipeListItem>>

    @GET("Recipe/RecipeInfo")
    suspend fun getRecipeInfo(
        @Query("token") token: String,
        @Query("recipe_id") recipeId: Int
    ): RawResult<RecipeInfo>

    @GET("Recipe/ExamInfo")
    suspend fun getExamInfo(
        @Query("token") token: String,
        @Query("exam_id") resultId: Int
    ): RawResult<ExamInfo>

    @GET("Recipe/PrescriptionInfo")
    suspend fun getPrescriptionInfo(
        @Query("token") token: String,
        @Query("prescription_id") prescriptionId: Int
    ): RawResult<PrescriptionInfo>
}
