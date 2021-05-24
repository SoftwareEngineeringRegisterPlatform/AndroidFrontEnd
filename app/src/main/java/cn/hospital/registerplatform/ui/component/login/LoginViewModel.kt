package cn.hospital.registerplatform.ui.component.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.ui.base.BaseViewModel


//@HiltViewModel
class LoginViewModel
//@Inject constructor(private val loginRepository: LoginRepository)
    : BaseViewModel() {

    private val _loginMode = MutableLiveData(LoginMethod.VERIFICATION_CODE)
    val loginMode: LiveData<LoginMethod> = _loginMode

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun login(username: String, password: String) {
//        val result = loginRepository.login(username, password)
//
//        if (result is Result.Success) {
//            _loginResult.value =
//                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
    }

    fun loginDataChanged(username: String, password: String) {
        when (_loginMode.value) {
            LoginMethod.VERIFICATION_CODE -> {
                if (!isUserNameValid(username)) {
                    _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
                } else if (!isPasswordValid(password)) {
                    _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
                } else {
                    _loginForm.value = LoginFormState(isDataValid = true)
                }
            }
            LoginMethod.PASSWORD -> {
                if (!isUserNameValid(username)) {
                    _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
                } else if (!isPasswordValid(password)) {
                    _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
                } else {
                    _loginForm.value = LoginFormState(isDataValid = true)
                }
            }
        }
    }

    fun switchLoginMethod() {

    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return if (phoneNumber.length == 11) {
            Patterns.PHONE.matcher(phoneNumber).matches()
        } else {
            phoneNumber.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isVerificationCodeValid(verificationCode: String): Boolean {
        return verificationCode.matches(Regex("[0-9]{6}"))
    }
}


data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)

enum class LoginMethod {
    VERIFICATION_CODE,
    PASSWORD
}