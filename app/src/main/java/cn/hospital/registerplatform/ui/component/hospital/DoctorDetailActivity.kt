package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.CommentListItem
import cn.hospital.registerplatform.data.dto.ScheduleInfo
import cn.hospital.registerplatform.data.repository.CommentRatingFilter
import cn.hospital.registerplatform.data.repository.CommentSortMethod
import cn.hospital.registerplatform.databinding.ActivityDoctorDetailBinding
import cn.hospital.registerplatform.databinding.ItemCommentListBinding
import cn.hospital.registerplatform.databinding.ItemScheduleDetailBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.comment.CommentViewModel
import cn.hospital.registerplatform.ui.component.register.RegisterScheduleActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DoctorDetailActivity : ActionBarActivity("医生详情") {
    private val mBinding: ActivityDoctorDetailBinding by databind(R.layout.activity_doctor_detail)
    private val hospitalViewModel: HospitalViewModel by viewModels()
    private val commentViewModel: CommentViewModel by viewModels()

    private lateinit var scheduleAdapter: HospitalListAdapter<ScheduleInfo, ItemScheduleDetailBinding>
    private lateinit var commentListItemAdapter: HospitalPagingAdapter<CommentListItem, ItemCommentListBinding>
    private var doctorId by Delegates.notNull<Int>()

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            commentViewModel.getCommentList(doctorId).collect {
                commentListItemAdapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doctorId = intent.getIntExtra(KEY_DOCTOR_ID, 0)
        scheduleAdapter =
            HospitalListAdapter(listOf(), R.layout.item_schedule_detail) { binding, data ->
                binding.info = data
                binding.scheduleButton.setOnClickListener {
                    startActivity(RegisterScheduleActivity.newIntent(this, data))
                }
            }
        commentListItemAdapter =
            HospitalPagingAdapter(R.layout.item_comment_list) { binding, data ->
                binding.item = data
            }
        }
        commentListItemAdapter = HospitalPagingAdapter(R.layout.item_comment_list) { binding, data ->
            binding.item = data
        }
        mBinding.apply {
            lifecycleOwner = this@DoctorDetailActivity
            scheduleContainer.adapter = scheduleAdapter
            commentContainer.adapter = commentListItemAdapter
            getList()
        }
        hospitalViewModel.getDoctorInfo(doctorId).observe(this@DoctorDetailActivity) { res ->
            res.doSuccess {
                mBinding.apply{
                    info = it
                    Log.d("dept", it.departmentName)
                    Log.d("rating", it.averageRating.toString())
                    ratingBar.rating = it.averageRating
                    commentTitle.text = getString(R.string.user_comment, it.commentsNum)
                    scoreOverview.text = getString(R.string.comment_overview_title, it.averageRating)
                    doctorDepartment.setSelected(true)
                    doctorHospital.setSelected(true)
                    executePendingBindings()
                }
            }
        }
        hospitalViewModel.getDoctorScheduleList(doctorId)
            .observe(this@DoctorDetailActivity) { res ->
                res.doSuccess {
                    scheduleAdapter.updateList(it.sortedBy { info -> info.begin_time })
                }
            }


        mBinding.userCommentSortMethod.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                commentViewModel.mSortMethod = CommentSortMethod.fromInt(position)
                getList()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                parent?.setSelection(0)
            }
        }

        mBinding.userCommentSelectMethod.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                commentViewModel.mRatingFilter = CommentRatingFilter.fromInt(position)
                getList()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                parent?.setSelection(0)
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
