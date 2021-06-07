package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.databinding.ActivityHospitalDetailBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.base.BaseActivity
import cn.hospital.registerplatform.ui.component.comment.CommentViewModel
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class HospitalDetailActivity : ActionBarActivity("医院详情") {
    private val mBinding: ActivityHospitalDetailBinding by databind(R.layout.activity_hospital_detail)
    private val commentViewModel: CommentViewModel by viewModels()
    private val hospitalViewModel: HospitalViewModel by viewModels()

    //    private lateinit var commentListAdapter: CommentListAdapter
//    private var getListJob: Job? = null
    private var hospitalId by Delegates.notNull<Int>()

    //    private fun getList() {
//        getListJob?.cancel()
//        getListJob = lifecycleScope.launch {
//            commentViewModel.getCommentList(hospitalId).collect {
//                commentListAdapter.submitData(it)
//            }
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hospitalId = intent.getIntExtra(KEY_HOSPITAL_ID, 0)
//        commentListAdapter = CommentListAdapter()
        mBinding.apply {
            lifecycleOwner = this@HospitalDetailActivity
//            fakeDetailHeader.setOnClickListener {
//                startActivity(CommentListActivity.newIntent(this@HospitalDetailActivity, 0))
//            }
//            commentListContainer.adapter = commentListAdapter
//            getList()
            buttonRegister.setOnClickListener {
                startActivity(DepartmentListActivity.newIntent(this@HospitalDetailActivity, hospitalId))
            }
        }
        hospitalViewModel.getHospitalInfo(hospitalId).observe(this) {
            it.doSuccess { info ->
                mBinding.info = info
                mBinding.executePendingBindings()
            }
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
