package cn.hospital.registerplatform.data.repository

import android.text.format.DateFormat
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cn.hospital.registerplatform.api.Resource
import cn.hospital.registerplatform.api.interfaces.HospitalApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.*
import cn.hospital.registerplatform.data.pagingsource.HospitalPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class HospitalRepository(
    private val hospitalApi: HospitalApi,
    private val userPreference: UserPreference,
    private val pagingConfig: PagingConfig
) {
    fun getHospitalList() = getList { loadType, page, size ->
        hospitalApi.getHospitalList(loadType, page, size)
    }

    fun getDepartmentList(hospitalId: Int) = getList { loadType, page, size ->
        hospitalApi.getDepartmentList(hospitalId, loadType, page, size)
    }

    fun getDoctorList(departmentId: Int) = getList { countType, page, size ->
        hospitalApi.getDoctorList(departmentId, countType, page, size)
    }

    fun getAllDoctorList(departmentId: Int) = suspendFunctionToFlow<List<DoctorListItem>, List<DoctorListItem>> {
        hospitalApi.getAllDoctorList(departmentId)
    }

    fun getDepartmentScheduleList(departmentId: Int) =
        suspendFunctionToFlow<List<ScheduleInfo>, Map<String, List<ScheduleInfo>>>(
            successHandler = { flowCollector, t ->
                flowCollector.emit(Resource.Success(t.groupBy { info ->
                    DateFormat.format("MM-dd", info.begin_time).toString()
                }))
            }
        ) { hospitalApi.getDepartmentSchedule(departmentId) }

    fun getDoctorScheduleList(doctorId: Int) = suspendFunctionToFlow<List<ScheduleInfo>, List<ScheduleInfo>> {
        hospitalApi.getDoctorSchedule(doctorId)
    }

    fun getHospitalInfo(hospitalId: Int) = suspendFunctionToFlow<HospitalInfo, HospitalInfo> {
        hospitalApi.getHospitalInfo(hospitalId)
    }

    fun getDepartmentInfo(departmentId: Int) = suspendFunctionToFlow<DepartmentInfo, DepartmentInfo> {
        hospitalApi.getDepartmentInfo(departmentId)
    }

    fun getDoctorInfo(doctorId: Int) = suspendFunctionToFlow<DoctorInfo, DoctorInfo> {
        hospitalApi.getDoctorInfo(doctorId)
    }

    private fun <T : Any> getList(
        getListFromApi: suspend (LoadType, page: Int, size: Int) -> RawResult<List<T>>
    ): Flow<PagingData<T>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                HospitalPagingSource { page: Int, size: Int ->
                    try {
                        val rawResult = getListFromApi(LoadType.PAGE, page, size)
                        if (rawResult.success) rawResult.content else listOf()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        listOf()
                    }
                }
            }
        ).flow.flowOn(Dispatchers.IO)
    }
}
