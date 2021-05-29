package cn.hospital.registerplatform.ui.component.comment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.Resource
import cn.hospital.registerplatform.data.dto.UploadComment
import cn.hospital.registerplatform.databinding.ActivitySubmitCommentBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import cn.hospital.registerplatform.ui.component.main.MainActivity
import cn.hospital.registerplatform.utils.ToastUtils
import com.google.android.material.chip.Chip
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class SubmitCommentActivity : BaseActivity() {
    private val mBinding: ActivitySubmitCommentBinding by databind(R.layout.activity_submit_comment)
    private val mViewModel: CommentViewModel by viewModels()

    private var hospitalId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hospitalId = intent.getIntExtra(KEY_HOSPITAL_ID, 0)
        mBinding.apply {
            lifecycleOwner = this@SubmitCommentActivity
            ratingBar.rating = 5F
            submitComment.setOnClickListener {
                mViewModel.submitComment(
                    hospitalId,
                    UploadComment(
                        "测试服务类型",
                        "测试疾病",
                        ratingBar.rating.toInt(),
                        commentDetail.text.toString(),
                        tagsContainer.children.filter {
                            (it as Chip).isChecked
                        }.map {
                            (it as Chip).text.toString()
                        }.toList()
                    )
                ).observe(this@SubmitCommentActivity, { res ->
                    if (res is Resource.Success && res.value.success) {
                        ToastUtils.show(this@SubmitCommentActivity, "上传成功，2秒后返回主页")
                        lifecycleScope.launch {
                            delay(2000)
                            startActivity(
                                Intent(
                                    this@SubmitCommentActivity,
                                    MainActivity::class.java
                                )
                            )
                        }
                    } else {
                        ToastUtils.show(this@SubmitCommentActivity, "上传失败")
                    }
                })
            }
        }
    }

    companion object {
        private const val KEY_HOSPITAL_ID = "key_hospital_id"
        fun newIntent(context: Context, hospitalId: Int): Intent {
            return Intent(context, SubmitCommentActivity::class.java).apply {
                putExtra(KEY_HOSPITAL_ID, hospitalId)
            }
        }
    }
}
