package cn.hospital.registerplatform.data.repository

import androidx.paging.PagingConfig
import cn.hospital.registerplatform.api.interfaces.RegisterApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.RegisterInfo
import cn.hospital.registerplatform.data.dto.suspendFunctionToFlow
import cn.hospital.registerplatform.data.pagingsource.getList

class RegisterRepository(
    private val registerApi: RegisterApi,
    private val userPreference: UserPreference,
    private val pagingConfig: PagingConfig
) {
    fun registerSchedule(scheduleId: Int) = suspendFunctionToFlow<String> {
        registerApi.registerSchedule(userPreference.getCachedToken(), scheduleId)
    }

    fun getRegisterInfo(registerId: Int) = suspendFunctionToFlow<RegisterInfo> {
        registerApi.getRegisterInfo(userPreference.getCachedToken(), registerId)
    }

    fun getRegisterList() = getList(pagingConfig) { loadType, page, size ->
        registerApi.getRegisterList(userPreference.getCachedToken(), loadType, page, size)
    }
}
