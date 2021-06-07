package cn.hospital.registerplatform.api.interfaces

import cn.hospital.registerplatform.data.dto.NewUserData
import cn.hospital.registerplatform.data.dto.RawResult
import cn.hospital.registerplatform.data.dto.UserInfo
import cn.hospital.registerplatform.data.dto.WrapUploadUserInfo
import retrofit2.http.*

interface UserApi {
    @Headers("Content-Type: application/json")
    @POST("User/NewUser")
    suspend fun newUser(
        @Body newUserData: NewUserData
    ): RawResult<String>

    @FormUrlEncoded
    @POST("User/SignIn")
    suspend fun logInViaPassword(
        @Field("phone_number") phoneNumber: String,
        @Field("password") password: String,
    ): RawResult<String>

    @FormUrlEncoded
    @POST("User/SendVerification")
    suspend fun sendVerification(
        @Field("phone_number") phoneNumber: String
    ): RawResult<String>

    @FormUrlEncoded
    @POST("User/SignIn")
    suspend fun logInViaVerification(
        @Field("phone_number") phoneNumber: String,
        @Field("verification") verificationCode: String
    ): RawResult<String>

    @Headers("Content-Type: application/json")
    @POST("User/UpdateInfo")
    suspend fun updateInfo(
        @Body uploadUserInfo: WrapUploadUserInfo
    ): RawResult<String>

    @GET("User/GetInfo")
    suspend fun getInfo(
        @Query("token") token: String
    ): RawResult<UserInfo>
}
