package cn.hospital.registerplatform.ui.component.hospital

import androidx.lifecycle.asLiveData
import cn.hospital.registerplatform.data.repository.HospitalRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HospitalViewModel @Inject constructor(private val hospitalRepository: HospitalRepository) : BaseViewModel() {
    fun getHospitalList() = hospitalRepository.getHospitalList()

    fun getDepartmentList(hospitalId: Int) = hospitalRepository.getDepartmentList(hospitalId)

    fun getDoctorList(departmentId: Int) = hospitalRepository.getDoctorList(departmentId)

    fun getAllDoctorList(departmentId: Int) = hospitalRepository.getAllDoctorList(departmentId).asLiveData()

    fun getHospitalInfo(hospitalId: Int) = hospitalRepository.getHospitalInfo(hospitalId).asLiveData()

    fun getDepartmentInfo(departmentId: Int) = hospitalRepository.getDepartmentInfo(departmentId).asLiveData()

    fun getDoctorInfo(doctorId: Int) = hospitalRepository.getDoctorInfo(doctorId).asLiveData()

    fun getScheduleInfo(scheduleId: Int) = hospitalRepository.getScheduleInfo(scheduleId).asLiveData()

    fun getDepartmentScheduleList(departmentId: Int) =
        hospitalRepository.getDepartmentScheduleList(departmentId).asLiveData()

    fun getDoctorScheduleList(doctorId: Int) = hospitalRepository.getDoctorScheduleList(doctorId).asLiveData()
}
