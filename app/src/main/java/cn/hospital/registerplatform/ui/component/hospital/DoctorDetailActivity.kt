package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Spinner
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.CommentListItem
import cn.hospital.registerplatform.data.dto.ScheduleInfo
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
    private var sortMethod by Delegates.notNull<String>()
    private var sortSelect by Delegates.notNull<Int>()

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            commentViewModel.getCommentList(doctorId, sortMethod, sortSelect).collect {
                commentListItemAdapter.submitData(it)
            }
        }
    }

//    private var user_comment_sort_method_spinner = findViewById<Spinner>(R.id.user_comment_sort_method)
//    private var user_comment_select_method_spinner = findViewById<Spinner>(R.id.user_comment_select_method)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doctorId = intent.getIntExtra(KEY_DOCTOR_ID, 0)
        sortMethod = "rating"
        sortSelect = 0
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
        mBinding.apply {
            lifecycleOwner = this@DoctorDetailActivity
            scheduleContainer.adapter = scheduleAdapter
            commentContainer.adapter = commentListItemAdapter
            getList()
        }
        hospitalViewModel.getDoctorInfo(doctorId).observe(this@DoctorDetailActivity) { res ->
            res.doSuccess {
                mBinding.info = it
                mBinding.executePendingBindings()
                findViewById<AppCompatTextView>(R.id.comment_overview_score).text = it.averageRating.toString()
                findViewById<AppCompatTextView>(R.id.comment_overview_people).text = it.commentsNum.toString() + "人"
            }
        }
        hospitalViewModel.getDoctorScheduleList(doctorId)
            .observe(this@DoctorDetailActivity) { res ->
                res.doSuccess {
                    scheduleAdapter.updateList(it.sortedBy { info -> info.begin_time })
                }
            }


        findViewById<Spinner>(R.id.user_comment_sort_method).onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    if (selectedItem.equals("最新评论优先")) {
                        sortMethod = "-last_edit_time"
                    } else if (selectedItem.equals("最旧评论优先")) {
                        sortMethod = "last_edit_time"
                    } else if (selectedItem.equals("最高评价优先")) {
                        sortMethod = "-rating"
                    } else if (selectedItem.equals("最低评价优先")) {
                        sortMethod = "rating"
                    }
                    mBinding.apply {
                        lifecycleOwner = this@DoctorDetailActivity
                        scheduleContainer.adapter = scheduleAdapter
                        commentContainer.adapter = commentListItemAdapter
                        getList()
                    }
                }
            }

        findViewById<Spinner>(R.id.user_comment_select_method).onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if (selectedItem.equals("全部评论")) { sortSelect = 0 }
                else if (selectedItem.equals("五星评论")) { sortSelect = 5 }
                else if (selectedItem.equals("四星评论")) { sortSelect = 4 }
                else if (selectedItem.equals("三星评论")) { sortSelect = 3 }
                else if (selectedItem.equals("二星评论")) { sortSelect = 2 }
                else if (selectedItem.equals("一星评论")) { sortSelect = 1 }
                mBinding.apply {
                    lifecycleOwner = this@DoctorDetailActivity
                    scheduleContainer.adapter = scheduleAdapter
                    commentContainer.adapter = commentListItemAdapter
                    getList()
                }
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
