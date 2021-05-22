package cn.hospital.registerplatform.api.interfaces

import cn.hospital.registerplatform.data.dto.CreateCommentResult
import cn.hospital.registerplatform.data.dto.RawCommentList
import cn.hospital.registerplatform.data.dto.UploadComment
import retrofit2.http.*

interface CommentApi {
    @GET("comments")
    suspend fun getComments(
        @Query("hospital_id") hospitalId: Int,
        @Query("page") page: Int?,
        @Query("page_size") pageSize: Int?
    ): RawCommentList

    @Headers("Content-Type: application/json")
    @POST("upload_comment")
    suspend fun createComments(
        @Header("token") token: String,
        @Query("hospital_id") hospitalId: Int,
        @Body comment: UploadComment
    ): CreateCommentResult

}