package cn.hospital.registerplatform.ui.component.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doFailure
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.databinding.ActivityNewUserBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.utils.ToastUtils
import cn.hospital.registerplatform.utils.delayLaunch
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewUserActivity : ActionBarActivity("新建用户") {
    private val mBinding: ActivityNewUserBinding by databind(R.layout.activity_new_user)
    private val mViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@NewUserActivity
            createUserButton.setOnClickListener {
                mViewModel.createUser(
                    usernameInput.text.toString(),
                    phoneNumberInput.text.toString(),
                    passwordInput.text.toString(),
                ).observe(this@NewUserActivity) {
                    it.doSuccess { token ->
                        mViewModel.saveToken(token)
                        ToastUtils.show(this@NewUserActivity, "注册成功")
                        lifecycleScope.delayLaunch {
                            startActivity(UploadInfoActivity.newClearIntent(this@NewUserActivity))
                        }
                    }
                    it.doFailure { exception ->
                        ToastUtils.show(this@NewUserActivity, "注册失败，原因：" + exception?.message)
                    }
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewUserActivity::class.java)
        }
    }
}
