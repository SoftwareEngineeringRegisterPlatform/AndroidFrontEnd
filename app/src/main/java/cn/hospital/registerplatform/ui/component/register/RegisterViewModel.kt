package cn.hospital.registerplatform.ui.component.register

import android.content.Context
import androidx.lifecycle.asLiveData
import cn.hospital.registerplatform.data.repository.RegisterRepository
import cn.hospital.registerplatform.data.repository.UserRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import cn.hospital.registerplatform.ui.base.LoginOperationInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val registerRepository: RegisterRepository,
    private val userRepository: UserRepository
) : BaseViewModel(), LoginOperationInterface by userRepository {
    fun registerSchedule(scheduleId: Int) = registerRepository.registerSchedule(scheduleId).asLiveData()

    fun getRegisterList() = registerRepository.getRegisterList()
}
