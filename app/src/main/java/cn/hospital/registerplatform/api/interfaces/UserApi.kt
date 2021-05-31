package cn.hospital.registerplatform.api.interfaces

import cn.hospital.registerplatform.data.dto.NewUserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {
    @Headers("Content-Type: application/json")
    @POST("User/NewUser")
    suspend fun newUser(
        @Body userinfo: NewUserInfo
    )

    @POST("User/SignIn")
    suspend fun signInViaPassword(

    )

    @POST("User/SendVerification")
    suspend fun sendVerification(

    )

    @POST("User/SignIn")
    suspend fun signInViaPhone(

    )

    @GET("User/GetInfo")
    suspend fun getInfo(

    )
}
