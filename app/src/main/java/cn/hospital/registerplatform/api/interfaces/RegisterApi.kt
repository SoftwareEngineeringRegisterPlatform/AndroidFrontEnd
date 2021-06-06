package cn.hospital.registerplatform.api.interfaces

import cn.hospital.registerplatform.data.dto.LoadType
import cn.hospital.registerplatform.data.dto.RawResult
import cn.hospital.registerplatform.data.dto.RegisterInfo
import cn.hospital.registerplatform.data.dto.RegisterListItem
import retrofit2.http.*

interface RegisterApi {
    @FormUrlEncoded
    @POST("Regist/Regist")
    suspend fun registerSchedule(
        @Field("token") token: String,
        @Field("schedule_id") scheduleId: Int
    ): RawResult<String>

    @GET("Regist/RegistList")
    suspend fun getRegisterList(
        @Query("token") token: String,
        @Query("loadType") LoadType: LoadType,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RawResult<List<RegisterListItem>>

    @GET("Regist/RegistInfo")
    suspend fun getRegisterInfo(
        @Query("token") token: String,
        @Query("regist_id") registerId: Int
    ): RawResult<RegisterInfo>
}
