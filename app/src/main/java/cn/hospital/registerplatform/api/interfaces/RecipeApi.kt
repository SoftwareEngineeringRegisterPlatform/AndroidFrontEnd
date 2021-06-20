package cn.hospital.registerplatform.api.interfaces

import cn.hospital.registerplatform.data.dto.*
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
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


    @GET("Recipe/DoctorRecipeList")
    suspend fun getDoctorRecipeList(
        @Query("token") token: String,
        @Query("loadType") LoadType: LoadType
    ): RawResult<List<RecipeListItem>>

    @POST("Recipe/SubmitRecipe")
    suspend fun submitRecipe(
        @Field("token") token: String,
        @Field("recipe_info") recipe_info: RecipeInfoSubmit
    ): RawResult<String>

    @POST("Recipe/SubmitExam")
    suspend fun submitExam(
        @Field("token") token: String,
        @Field("exam_info") exam_info: ExamInfoSubmit
    ): RawResult<String>
    @POST("Recipe/SubmitPrescription")
    suspend fun submitPrescription(
        @Field("token") token: String,
        @Field("prescription_info") prescription_info: PrescriptionInfoSubmit
    ): RawResult<String>

    @POST("Recipe/EditRecipe")
    suspend fun editRecipe(
        @Field("token") token: String,
        @Field("recipe_id") recipeId: Int,
        @Field("recipe_info") recipeInfo: RecipeInfoEdit,
    ): RawResult<String>

    @POST("Recipe/EditExam")
    suspend fun editExam(
        @Field("token") token: String,
        @Field("recipe_id") recipeId: Int,
        @Field("exam_info") examInfo: ExamInfoEdit
    ): RawResult<String>

    @POST("Recipe/EditPrescription")
    suspend fun editPrescription(
        @Field("token") token: String,
        @Field("recipe_id") recipeId: Int,
        @Field("prescription_info") prescriptionInfo: PrescriptionInfoEdit
    ): RawResult<String>
}
