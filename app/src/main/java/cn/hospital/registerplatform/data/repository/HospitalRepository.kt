package cn.hospital.registerplatform.data.repository

import android.text.format.DateFormat
import androidx.paging.PagingConfig
import cn.hospital.registerplatform.api.Resource
import cn.hospital.registerplatform.api.interfaces.HospitalApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.*
import cn.hospital.registerplatform.data.pagingsource.getRawResultList
import com.google.gson.Gson

class HospitalRepository(
    private val hospitalApi: HospitalApi,
    private val userPreference: UserPreference,
    private val pagingConfig: PagingConfig
) {
    fun getHospitalList() = getRawResultList(pagingConfig) { loadType, page, size ->
        hospitalApi.getHospitalList(loadType, page, size)
    }

    fun getHospitalFilterList(name: String, type: String) = getRawResultList(pagingConfig) { loadType, page, size ->
        if (type == "所有") {
            // hospitalApi.getHospitalList(loadType, page, size)
            hospitalApi.getHospitalFilterList(name, loadType, page, size)
        } else {
            hospitalApi.getHospitalFilterList(Gson().toJson(mapOf("name" to name, "type" to type)), loadType, page, size)
        }
    }

    fun getDepartmentList(hospitalId: Int) = getRawResultList(pagingConfig) { loadType, page, size ->
        hospitalApi.getDepartmentList(hospitalId, loadType, page, size)
    }

    fun getDoctorList(departmentId: Int) = getRawResultList(pagingConfig) { countType, page, size ->
        hospitalApi.getDoctorList(departmentId, countType, page, size)
    }

    fun getAllDoctorList(departmentId: Int) = suspendFunctionToFlow<List<DoctorListItem>> {
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

    fun getDoctorScheduleList(doctorId: Int) = suspendFunctionToFlow<List<ScheduleInfo>> {
        hospitalApi.getDoctorSchedule(doctorId)
    }

    fun getHospitalInfo(hospitalId: Int) = suspendFunctionToFlow<HospitalInfo> {
        hospitalApi.getHospitalInfo(hospitalId)
    }

    fun getDepartmentInfo(departmentId: Int) = suspendFunctionToFlow<DepartmentInfo> {
        hospitalApi.getDepartmentInfo(departmentId)
    }

    fun getDoctorInfo(doctorId: Int) = suspendFunctionToFlow<DoctorInfo> {
        hospitalApi.getDoctorInfo(doctorId)
    }

    fun getScheduleInfo(scheduleId: Int) = suspendFunctionToFlow<ScheduleInfo> {
        hospitalApi.getScheduleInfo(scheduleId)
    }
}
