package cn.hospital.registerplatform.ui.component.register

import androidx.lifecycle.asLiveData
import cn.hospital.registerplatform.data.repository.RegisterRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(private val registerRepository: RegisterRepository) : BaseViewModel() {
    fun registerSchedule(scheduleId: Int) = registerRepository.registerSchedule(scheduleId).asLiveData()

    fun getRegisterList() = registerRepository.getRegisterList()
}
