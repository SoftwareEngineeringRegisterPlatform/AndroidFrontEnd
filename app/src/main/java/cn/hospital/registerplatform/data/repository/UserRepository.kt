package cn.hospital.registerplatform.data.repository

import android.content.Context
import cn.hospital.registerplatform.DEFAULT_TOKEN
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.Resource
import cn.hospital.registerplatform.api.interfaces.UserApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.NewUserData
import cn.hospital.registerplatform.data.dto.UserInfo
import cn.hospital.registerplatform.data.dto.WrapUploadUserInfo
import cn.hospital.registerplatform.data.dto.suspendFunctionToFlow
import cn.hospital.registerplatform.ui.base.LoginOperationInterface
import cn.hospital.registerplatform.utils.ToastUtils

class UserRepository(
    private val userApi: UserApi,
    private val userPreference: UserPreference
) : LoginOperationInterface {
    fun createUser(userName: String, phoneNumber: String, password: String) = suspendFunctionToFlow<String> {
        userApi.newUser(NewUserData(userName, phoneNumber, password))
    }

    fun changePwd(oldPwd: String, newPwd: String) = suspendFunctionToFlow<String> {
        userApi.changPwd(getToken(), oldPwd, newPwd)
    }

    fun resetPwd(phone: String, verification: String, newPwd: String) = suspendFunctionToFlow<String> {
        userApi.resetPwd(phone, verification, newPwd)
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

    fun sendVerification(phoneNumber: String) = suspendFunctionToFlow<String> {
        userApi.sendVerification(phoneNumber)
    }

    fun getUserInfo() = suspendFunctionToFlow<UserInfo> {
        userApi.getInfo(userPreference.getCachedToken())
    }

    fun updateInfo(userInfo: WrapUploadUserInfo) = suspendFunctionToFlow<String> {
        userApi.updateInfo(userInfo)
    }

    override suspend fun requireLogin(): Boolean {
        return if (userPreference.getCachedToken() == DEFAULT_TOKEN) {
            true
        } else {
            try {
                val info = userApi.getInfo(userPreference.getCachedToken())
                !info.success
            } catch (e: Exception) {
                true
            }
        }
    }

    override suspend fun needLoginToast(
        context: Context,
        ifLoginOperation: () -> Unit,
        elseOperation: (() -> Unit)?
    ) {
        if (this.requireLogin()) {
            ToastUtils.show(context, R.string.need_login_toast_content)
            elseOperation?.invoke()
        } else {
            ifLoginOperation()
        }
    }

    override suspend fun needLoginToast(
        context: Context,
        ifLoginOperation: () -> Unit
    ) {
        needLoginToast(context, ifLoginOperation, null)
    }

    fun clearToken(): Boolean = userPreference.cacheToken(DEFAULT_TOKEN)

    fun saveToken(token: String): Boolean = userPreference.cacheToken(token)
    fun getToken(): String = userPreference.getCachedToken()
}
