package cn.hospital.registerplatform.ui.component.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.databinding.ActivityLoginBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.main.MainActivity
import cn.hospital.registerplatform.utils.ToastUtils
import cn.hospital.registerplatform.utils.afterTextChanged
import cn.hospital.registerplatform.utils.launchPeriodicAsync
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ActionBarActivity("登陆") {

    private val loginViewModel: LoginViewModel by viewModels()
    private val binding: ActivityLoginBinding by databind(R.layout.activity_login)

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            lifecycleOwner = this@LoginActivity
            loginViewModel.loginMethod.observe(this@LoginActivity) {
                when (it) {
                    LoginMethod.VERIFICATION_CODE -> {
                        passwordContainer.visibility = View.INVISIBLE
                        verificationContainer.visibility = View.VISIBLE
                    }
                    LoginMethod.PASSWORD -> {
                        passwordContainer.visibility = View.VISIBLE
                        verificationContainer.visibility = View.INVISIBLE
                    }
                    else -> {
                        ToastUtils.show(this@LoginActivity, "发生异常")
                    }
                }
            }
            binding.switchLoginMethod.setOnClickListener {
                loginViewModel.switchLoginMethod()
            }
            loginViewModel.loginFormState.observe(this@LoginActivity) {
                val loginState = it ?: return@observe
                login.isEnabled = loginState.isDataValid
                sendVerificationCode.isEnabled = true
                if (loginState.phoneNumberError != null) {
                    phoneNumber.error = getString(loginState.phoneNumberError)
                    sendVerificationCode.isEnabled = false
                }
                if (loginState.passwordError != null) {
                    password.error = getString(loginState.passwordError)
                }
                if (loginState.verificationError != null) {
                    verificationCode.error = getString(loginState.verificationError)
                }
            }
            phoneNumber.afterTextChanged {
                loginDataChanged()
            }
            password.afterTextChanged {
                loginDataChanged()
            }
            verificationCode.afterTextChanged {
                loginDataChanged()
            }
            login.setOnClickListener {
                val pass = when (loginViewModel.loginMethod.value) {
                    LoginMethod.PASSWORD -> password.text.toString()
                    LoginMethod.VERIFICATION_CODE -> verificationCode.text.toString()
                    else -> ""
                }
                loginViewModel.login(phoneNumber.text.toString(), pass)
                    .observe(this@LoginActivity) {
                        it.doSuccess {
                            ToastUtils.show(this@LoginActivity, "登陆成功")
                            lifecycleScope.launch {
                                delay(1000)
                                startActivity(MainActivity.newIntent(this@LoginActivity))
                            }
                        }
                    }
            }
            sendVerificationCode.setOnClickListener {
                loginViewModel.sendVerificationCode(phoneNumber.text.toString()).observe(this@LoginActivity) {
                    it.doSuccess {
                        ToastUtils.show(this@LoginActivity, "验证码发送成功")
                        job?.cancel()
                        job = lifecycleScope.launchPeriodicAsync(
                            100,
                            600,
                            { second ->
                                sendVerificationCode.isEnabled = false
                                sendVerificationCode.text = (second / 10).toString()
                            },
                            {
                                sendVerificationCode.isEnabled = true
                            }
                        )
                    }
                }
            }
        }
    }

    private fun loginDataChanged() {
        when (loginViewModel.loginMethod.value) {
            LoginMethod.VERIFICATION_CODE -> {
                loginViewModel.loginDataChanged(
                    binding.phoneNumber.text.toString(),
                    binding.verificationCode.text.toString()
                )
            }
            LoginMethod.PASSWORD -> {
                loginViewModel.loginDataChanged(
                    binding.phoneNumber.text.toString(),
                    binding.password.text.toString()
                )
            }
            else -> {
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}
