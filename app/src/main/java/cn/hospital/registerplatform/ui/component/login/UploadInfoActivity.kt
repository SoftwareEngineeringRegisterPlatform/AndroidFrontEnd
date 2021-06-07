package cn.hospital.registerplatform.ui.component.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doFailure
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.databinding.ActivityUploadInfoBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.main.MainActivity
import cn.hospital.registerplatform.utils.ToastUtils
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UploadInfoActivity : ActionBarActivity("上传用户信息") {
    private val mBinding: ActivityUploadInfoBinding by databind(R.layout.activity_upload_info)
    private val mViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@UploadInfoActivity
            uploadButton.setOnClickListener {
                mViewModel.updateInfo(
                    usernameInput.text.toString(),
                    when (sexSelector.checkedRadioButtonId) {
                        man.id -> "男"
                        woman.id -> "女"
                        other.id -> "其他"
                        else -> "其他"
                    },
                    when (bloodTypeSelector.checkedRadioButtonId) {
                        bloodTypeA.id -> "A"
                        bloodTypeB.id -> "B"
                        bloodTypeAb.id -> "AB"
                        bloodTypeO.id -> "O"
                        else -> "A"
                    },
                    heightInput.text.toString().toDoubleOrNull() ?: 0.0,
                    weightInput.text.toString().toDoubleOrNull() ?: 0.0,
                    diseaseHistoryInput.text.toString()
                ).observe(this@UploadInfoActivity) {
                    it.doSuccess {
                        ToastUtils.show(this@UploadInfoActivity, "上传成功")
                        lifecycleScope.launch {
                            delay(1000)
                            startActivity(MainActivity.newClearIntent(this@UploadInfoActivity))
                        }
                    }
                    it.doFailure { exception ->
                        ToastUtils.show(this@UploadInfoActivity, "上传失败，原因：" + exception?.message)
                    }
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, UploadInfoActivity::class.java)
        }

        fun newClearIntent(context: Context): Intent {
            return Intent(context, UploadInfoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }
}
