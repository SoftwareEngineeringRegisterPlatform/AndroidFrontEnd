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

    fun getHospitalInfo(hospitalId: Int) = hospitalRepository.getHospitalInfo(hospitalId).asLiveData()

    fun getDepartmentInfo(departmentId: Int) = hospitalRepository.getDepartmentInfo(departmentId).asLiveData()

    fun getDoctorInfo(doctorId: Int) = hospitalRepository.getDoctorInfo(doctorId).asLiveData()
}
