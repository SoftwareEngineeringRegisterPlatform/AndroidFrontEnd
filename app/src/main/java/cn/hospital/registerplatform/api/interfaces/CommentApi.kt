package cn.hospital.registerplatform.api.interfaces

import cn.hospital.registerplatform.data.dto.CommentListItem
import cn.hospital.registerplatform.data.dto.LoadType
import cn.hospital.registerplatform.data.dto.RawResult
import cn.hospital.registerplatform.data.dto.UploadComment
import retrofit2.http.*

interface CommentApi {
    @GET("Evaluate/GetEva")
    suspend fun getComments(
        @Query("doctor_id") doctorId: Int,
        @Query("loadType") loadType: LoadType,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RawResult<List<CommentListItem>>

    @Headers("Content-Type: application/json")
    @POST("Evaluate/SubmitEva")
    suspend fun createComments(
        @Body comment: UploadComment
    ): RawResult<String>

    @GET("Evaluate/MyEva")
    suspend fun getMyComment(
        @Query("token") token: String,
        @Query("loadType") LoadType: LoadType,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RawResult<List<CommentListItem>>
}
