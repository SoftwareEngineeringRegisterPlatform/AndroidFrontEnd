package cn.hospital.registerplatform.ui.component.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doFailure
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.databinding.ActivityResetPwdBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.main.MainActivity
import cn.hospital.registerplatform.utils.ToastUtils
import cn.hospital.registerplatform.utils.delayLaunch
import cn.hospital.registerplatform.utils.launchPeriodicAsync
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class ResetPwdActivity : ActionBarActivity("忘记密码") {
    private val mBinding: ActivityResetPwdBinding by databind(R.layout.activity_reset_pwd)
    private val mViewModel: LoginViewModel by viewModels()
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@ResetPwdActivity
            sendVerificationCode2.setOnClickListener {
                mViewModel.sendVerificationCode(phoneNumberInput.text.toString())
                    .observe(this@ResetPwdActivity) {
                        it.doSuccess {
                            ToastUtils.show(this@ResetPwdActivity, "验证码发送成功")
                            job?.cancel()
                            job = lifecycleScope.launchPeriodicAsync(
                                100,
                                600,
                                { second ->
                                    sendVerificationCode2.isEnabled = false
                                    sendVerificationCode2.text = (second / 10).toString()
                                },
                                {
                                    sendVerificationCode2.isEnabled = true
                                }
                            )
                        }
                    }
            }

            resetPwd.setOnClickListener {
                mViewModel.resetPwd(
                    phoneNumberInput.text.toString(),
                    verificationCode.text.toString(),
                    passwordInput.text.toString()
                )
                    .observe(this@ResetPwdActivity) {
                        it.doSuccess {
                            ToastUtils.show(this@ResetPwdActivity, "密码修改成功")
                            lifecycleScope.delayLaunch {
                                startActivity(MainActivity.newClearIntent(this@ResetPwdActivity))
                            }
                        }
                        it.doFailure { exception ->
                            ToastUtils.show(this@ResetPwdActivity, "密码修改失败，原因：" + exception?.message)
                        }
                    }
            }

        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ResetPwdActivity::class.java)
        }

        fun newClearIntent(context: Context): Intent {
            return Intent(context, ResetPwdActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }
}


