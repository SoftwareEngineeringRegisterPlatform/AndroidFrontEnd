package cn.hospital.registerplatform.api.interfaces

import cn.hospital.registerplatform.data.dto.*
import retrofit2.http.*

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

    @GET("Regist/DoctorRegistWithRecipe")
    suspend fun getDoctorRegistWithRecipe(
        @Query("token") token: String,
        @Query("loadType") LoadType: LoadType
    ): RawResult<List<RegistWithRecipeListItem>>

    @GET("Regist/DoctorRegistWithoutRecipe")
    suspend fun getDoctorRegistWithoutRecipe(
        @Query("token") token: String,
        @Query("loadType") LoadType: LoadType
    ): RawResult<List<RegistWithRecipeListItem>>

    @Headers("Content-Type: application/json")
    @POST("Recipe/SubmitRecipe")
    suspend fun submitRecipe(
        @Field("token") token: String,
        @Field("recipe_info") recipe_info: RecipeInfoSubmit
    ): RawResult<String>

    @Headers("Content-Type: application/json")
    @POST("Recipe/SubmitExam")
    suspend fun submitExam(
        @Field("token") token: String,
        @Field("exam_info") exam_info: ExamInfoSubmit
    ): RawResult<String>
    @Headers("Content-Type: application/json")
    @POST("Recipe/SubmitPrescription")
    suspend fun submitPrescription(
        @Field("token") token: String,
        @Field("prescription_info") prescription_info: PrescriptionInfoSubmit
    ): RawResult<String>

    @Headers("Content-Type: application/json")
    @POST("Recipe/EditRecipe")
    suspend fun editRecipe(
        @Body editRecipe: RecipeInfoEditBody
    ): RawResult<String>

    @Headers("Content-Type: application/json")
    @POST("Recipe/EditExam")
    suspend fun editExam(
        @Body editExam: ExamInfoEditBody
    ): RawResult<String>

    @Headers("Content-Type: application/json")
    @POST("Recipe/EditPrescription")
    suspend fun editPrescription(
        @Body editPrescription: PrescriptionInfoEditBody
    ): RawResult<String>
}
