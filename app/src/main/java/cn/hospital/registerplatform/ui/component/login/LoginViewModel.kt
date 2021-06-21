package cn.hospital.registerplatform.ui.component.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.Resource
import cn.hospital.registerplatform.data.dto.UploadUserInfo
import cn.hospital.registerplatform.data.dto.WrapUploadUserInfo
import cn.hospital.registerplatform.data.repository.UserRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel
@Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    private val _loginMethod = MutableLiveData(LoginMethod.VERIFICATION_CODE)
    val loginMethod: LiveData<LoginMethod> = _loginMethod

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun sendVerificationCode(phoneNumber: String) = userRepository.sendVerification(phoneNumber).asLiveData()

    fun login(phoneNumber: String, password: String) =
        when (_loginMethod.value) {
            LoginMethod.VERIFICATION_CODE -> userRepository.logInViaVerification(phoneNumber, password).asLiveData()
            LoginMethod.PASSWORD -> userRepository.logInViaPassword(phoneNumber, password).asLiveData()
            else -> MutableLiveData<Resource<String>>().apply {
                postValue(Resource.Failure(Exception("loginMethod not initialized")))
            }
        }

    fun loginDataChanged(phoneNumber: String, password: String) {
        if (!isPhoneNumberValid(phoneNumber)) {
            _loginForm.value = LoginFormState(phoneNumberError = R.string.invalid_phone)
        } else {
            when (_loginMethod.value) {
                LoginMethod.VERIFICATION_CODE -> {
                    if (!isVerificationCodeValid(password)) {
                        _loginForm.value = LoginFormState(verificationError = R.string.invalid_verification_code)
                    } else {
                        _loginForm.value = LoginFormState(isDataValid = true)
                    }
                }
                LoginMethod.PASSWORD -> {
                    if (!isPasswordValid(password)) {
                        _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
                    } else {
                        _loginForm.value = LoginFormState(isDataValid = true)
                    }
                }
            }
        }
    }

    fun switchLoginMethod() {
        _loginMethod.postValue(
            when (_loginMethod.value) {
                LoginMethod.PASSWORD -> LoginMethod.VERIFICATION_CODE
                LoginMethod.VERIFICATION_CODE -> LoginMethod.PASSWORD
                else -> LoginMethod.VERIFICATION_CODE
            }
        )
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return Patterns.PHONE.matcher(phoneNumber).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isVerificationCodeValid(verificationCode: String): Boolean {
        return verificationCode.matches(Regex("[0-9]{6}"))
    }

    fun createUser(
        username: String,
        phoneNumber: String,
        password: String
    ) = userRepository.createUser(username, phoneNumber, password).asLiveData()

    fun saveToken(
        token: String
    ) = userRepository.saveToken(token)

    fun updateInfo(
        username: String,
        sex: String,
        bloodType: String,
        height: Double,
        weight: Double,
        diseaseHistory: String
    ) = userRepository.updateInfo(
        WrapUploadUserInfo(
            userRepository.getToken(),
            UploadUserInfo(
                username,
                sex,
                height,
                weight,
                bloodType,
                diseaseHistory
            )
        )
    ).asLiveData()

    fun changPwd(
        oldpwd: String,
        newpwd: String
    ) = userRepository.changePwd(oldpwd, newpwd).asLiveData()

    fun resetPwd(
        phone: String,
        veri: String,
        npwd: String
    ) = userRepository.resetPwd(phone,veri, npwd).asLiveData()
}


data class LoginFormState(
    val phoneNumberError: Int? = null,
    val passwordError: Int? = null,
    val verificationError: Int? = null,
    val isDataValid: Boolean = false
)

enum class LoginMethod {
    VERIFICATION_CODE,
    PASSWORD
}
