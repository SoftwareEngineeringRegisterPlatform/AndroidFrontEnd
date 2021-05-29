package cn.hospital.registerplatform.api.interfaces

import cn.hospital.registerplatform.data.dto.*
import retrofit2.http.GET
import retrofit2.http.Query

interface HospitalApi {
    @GET("Hospital/HospitalList")
    suspend fun getHospitalList(
        @Query("countType") countType: CountType = CountType.ALL,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RawResult<List<HospitalListItem>>

    @GET("Hospital/HospitalInfo")
    suspend fun getHospitalInfo(
        @Query("hospital_id") hospitalId: Int
    ): RawResult<HospitalInfo>

    @GET("Hospital/DepartmentList")
    suspend fun getDepartmentList(
        @Query("hospital_id") hospitalId: Int,
        @Query("countType") countType: CountType = CountType.ALL,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RawResult<List<DepartmentListItem>>

    @GET("Hospital/DepartmentInfo")
    suspend fun getDepartmentInfo(
        @Query("department_id") departmentId: Int
    ): RawResult<DepartmentInfo>

    @GET("Hospital/DoctorList")
    suspend fun getDoctorList(
        @Query("department_id") departmentId: Int,
        @Query("countType") countType: CountType = CountType.ALL,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RawResult<List<DoctorListItem>>

    @GET("Hospital/DoctorInfo")
    suspend fun getDoctorInfo(
        @Query("doctor_id") doctorId: Int
    ): RawResult<DoctorInfo>
}
