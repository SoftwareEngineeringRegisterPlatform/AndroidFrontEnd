package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.ScheduleInfo
import cn.hospital.registerplatform.data.dto.SingleComment
import cn.hospital.registerplatform.databinding.ActivityDoctorDetailBinding
import cn.hospital.registerplatform.databinding.ItemCommentListBinding
import cn.hospital.registerplatform.databinding.ItemScheduleDetailBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import cn.hospital.registerplatform.ui.component.comment.CommentViewModel
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DoctorDetailActivity : BaseActivity() {
    private val mBinding: ActivityDoctorDetailBinding by databind(R.layout.activity_doctor_detail)
    private val hospitalViewModel: HospitalViewModel by viewModels()
    private val commentViewModel: CommentViewModel by viewModels()

    private lateinit var scheduleAdapter: HospitalListAdapter<ScheduleInfo, ItemScheduleDetailBinding>
    private lateinit var commentAdapter: HospitalPagingAdapter<SingleComment, ItemCommentListBinding>
    private var doctorId by Delegates.notNull<Int>()

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            commentViewModel.getCommentList(doctorId).collect {
                commentAdapter.submitData(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "医生详情"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        doctorId = intent.getIntExtra(KEY_DOCTOR_ID, 0)
        scheduleAdapter = HospitalListAdapter(listOf(), R.layout.item_schedule_detail) { binding, data ->
            binding.info = data
            binding.scheduleButton.setOnClickListener {

            }
        }
        commentAdapter = HospitalPagingAdapter(R.layout.item_comment_list) { binding, data ->
            binding.item = data
        }
        mBinding.apply {
            lifecycleOwner = this@DoctorDetailActivity
            scheduleContainer.adapter = scheduleAdapter
            commentContainer.adapter = commentAdapter
            getList()
        }
        hospitalViewModel.getDoctorInfo(doctorId).observe(this@DoctorDetailActivity) { res ->
            res.doSuccess {
                mBinding.info = it
                mBinding.executePendingBindings()
            }
        }
        hospitalViewModel.getDoctorScheduleList(doctorId).observe(this@DoctorDetailActivity) { res ->
            res.doSuccess {
                scheduleAdapter.updateList(it.sortedBy { info -> info.begin_time })
            }
        }
    }

    companion object {
        private const val KEY_DOCTOR_ID = "key_doctor_id"
        fun newIntent(context: Context, doctorId: Int): Intent {
            return Intent(context, DoctorDetailActivity::class.java).apply {
                putExtra(KEY_DOCTOR_ID, doctorId)
            }
        }
    }
}
