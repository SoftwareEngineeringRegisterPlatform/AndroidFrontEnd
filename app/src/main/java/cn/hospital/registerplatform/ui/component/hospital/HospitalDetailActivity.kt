package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.databinding.ActivityHospitalDetailBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import cn.hospital.registerplatform.ui.component.comment.CommentListActivity
import cn.hospital.registerplatform.ui.component.comment.CommentListAdapter
import cn.hospital.registerplatform.ui.component.comment.CommentViewModel
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class HospitalDetailActivity : BaseActivity() {
    private val mBinding: ActivityHospitalDetailBinding by binding()
    private val mViewModel: CommentViewModel by viewModels()

    private lateinit var commentListAdapter: CommentListAdapter
    private var getListJob: Job? = null
    private var hospitalId by Delegates.notNull<Int>()

    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getCommentList(hospitalId).collect {
                commentListAdapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hospitalId = intent.getIntExtra(KEY_HOSPITAL_ID, 0)
        commentListAdapter = CommentListAdapter()
        mBinding.apply {
            lifecycleOwner = this@HospitalDetailActivity
            fakeDetailHeader.setOnClickListener {
                startActivity(CommentListActivity.newIntent(this@HospitalDetailActivity, 0))
            }
            commentListContainer.adapter = commentListAdapter
            getList()
        }
    }

    companion object {
        private const val KEY_HOSPITAL_ID = "key_hospital_id"
        fun newIntent(context: Context, hospitalId: Int): Intent {
            return Intent(context, HospitalDetailActivity::class.java).apply {
                putExtra(KEY_HOSPITAL_ID, hospitalId)
            }
        }
    }
}