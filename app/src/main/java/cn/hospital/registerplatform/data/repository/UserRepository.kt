package cn.hospital.registerplatform.data.repository

import cn.hospital.registerplatform.DEFAULT_TOKEN
import cn.hospital.registerplatform.api.Resource
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.api.interfaces.UserApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.NewUserData
import cn.hospital.registerplatform.data.dto.UserInfo
import cn.hospital.registerplatform.data.dto.suspendFunctionToFlow
import kotlinx.coroutines.flow.collectLatest

class UserRepository(
    private val userApi: UserApi,
    private val userPreference: UserPreference
) {
    fun createUser(userName: String, phoneNumber: String, password: String) = suspendFunctionToFlow<String, String>(
        { flowCollector, _ ->
            suspendFunctionToFlow<String, String> {
                userApi.logInViaPassword(phoneNumber, password)
            }.collectLatest {
                it.doSuccess { token ->
                    userPreference.cacheToken(token)
                }
                flowCollector.emit(it)
            }
        }
    ) {
        userApi.newUser(NewUserData(userName, phoneNumber, password))
    }

    fun logInViaPassword(phoneNumber: String, password: String) = suspendFunctionToFlow<String, String>(
        successHandler = { flowCollector, s ->
            userPreference.cacheToken(s)
            flowCollector.emit(Resource.Success(s))
        }
    ) {
        userApi.logInViaPassword(phoneNumber, password)
    }

    fun logInViaVerification(phoneNumber: String, verification: String) = suspendFunctionToFlow<String, String>(
        successHandler = { flowCollector, s ->
            userPreference.cacheToken(s)
            flowCollector.emit(Resource.Success(s))
        }
    ) {
        userApi.logInViaVerification(phoneNumber, verification)
    }

    fun sendVerification(phoneNumber: String) = suspendFunctionToFlow<String, String> {
        userApi.sendVerification(phoneNumber)
    }

    fun getUserInfo() = suspendFunctionToFlow<UserInfo, UserInfo> {
        userApi.getInfo(userPreference.getCachedToken())
    }

    fun requireLogin(): Boolean = userPreference.getCachedToken() == DEFAULT_TOKEN

    fun clearToken(): Boolean = userPreference.cacheToken(DEFAULT_TOKEN)
}
