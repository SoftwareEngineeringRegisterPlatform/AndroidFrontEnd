package cn.hospital.registerplatform.ui.component.comment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.ActivityCommentListBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class CommentListActivity : BaseActivity() {

    private val mBinding: ActivityCommentListBinding by databind(R.layout.activity_comment_list)
    private val mViewModel: CommentViewModel by viewModels()

    private lateinit var commentListAdapter: CommentListAdapter
    private var getListJob: Job? = null
    private var hospitalId by Delegates.notNull<Int>()

    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getCommentList(hospitalId).onCompletion {
                mBinding.eventRefresh.isRefreshing = false
            }.collect {
                commentListAdapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hospitalId = intent.getIntExtra(KEY_HOSPITAL_ID, 0)
        commentListAdapter = CommentListAdapter()
        mBinding.apply {
            lifecycleOwner = this@CommentListActivity
            eventRefresh.setOnRefreshListener {
                getList()
            }
            commentListContainer.adapter = commentListAdapter
            getList()
        }
    }

    companion object {
        private const val KEY_HOSPITAL_ID = "key_hospital_id"
        fun newIntent(context: Context, hospitalId: Int): Intent {
            return Intent(context, CommentListActivity::class.java).apply {
                putExtra(KEY_HOSPITAL_ID, hospitalId)
            }
        }
    }
}
