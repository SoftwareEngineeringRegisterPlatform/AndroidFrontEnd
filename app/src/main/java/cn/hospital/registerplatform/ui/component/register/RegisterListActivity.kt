package cn.hospital.registerplatform.ui.component.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.RegisterCombinedListItem
import cn.hospital.registerplatform.databinding.ActivityRegisterListBinding
import cn.hospital.registerplatform.databinding.ItemRegisterListBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.base.BasePagingAdapter
import cn.hospital.registerplatform.ui.component.comment.SubmitCommentActivity
import cn.hospital.registerplatform.ui.component.hospital.DoctorDetailActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterListActivity : ActionBarActivity("我的预约") {
    private val mBinding: ActivityRegisterListBinding by databind(R.layout.activity_register_list)
    private val mViewModel: RegisterViewModel by viewModels()

    private lateinit var registerAdapter: BasePagingAdapter<RegisterCombinedListItem, ItemRegisterListBinding>

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getRegisterList().collect {
                registerAdapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerAdapter = BasePagingAdapter(R.layout.item_register_list) { binding, data ->
            binding.info = data.registerListItem
            binding.doctorInfo = data.doctorInfo
            binding.commentButton.setOnClickListener {
                startActivity(SubmitCommentActivity.newIntent(this, data.id))
            }
            binding.doctorInfoContainer.setOnClickListener {
                startActivity(DoctorDetailActivity.newIntent(this, data.doctorInfo.id))
            }
        }
        mBinding.apply {
            lifecycleOwner = this@RegisterListActivity
            container.adapter = registerAdapter
            getList()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterListActivity::class.java)
        }
    }
}
