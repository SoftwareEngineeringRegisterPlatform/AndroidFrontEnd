package cn.hospital.registerplatform.api.interfaces

import cn.hospital.registerplatform.data.dto.*
import retrofit2.http.*

interface CommentApi {
    @GET("Evaluate/GetEva")
    suspend fun getComments(
        @Query("doctor_id") doctorId: Int,
        @Query("loadType") loadType: LoadType,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RawResult<List<SingleComment>>

    @Headers("Content-Type: application/json")
    @POST("upload_comment")
    suspend fun createComments(
        @Header("token") token: String,
        @Query("hospital_id") hospitalId: Int,
        @Body comment: UploadComment
    ): CreateCommentResult

}
