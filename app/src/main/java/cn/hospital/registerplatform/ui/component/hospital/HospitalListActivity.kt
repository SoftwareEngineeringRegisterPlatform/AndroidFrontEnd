package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.HospitalListItem
import cn.hospital.registerplatform.databinding.ActivityHospitalListBinding
import cn.hospital.registerplatform.databinding.ItemHospitalListBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HospitalListActivity : BaseActivity() {
    private val mBinding: ActivityHospitalListBinding by databind(R.layout.activity_hospital_list)
    private val mViewModel: HospitalViewModel by viewModels()

    private lateinit var hospitalAdapter: HospitalListAdapter<HospitalListItem, ItemHospitalListBinding>

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getHospitalList().collect {
                hospitalAdapter.submitData(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "医院列表"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hospitalAdapter = HospitalListAdapter(R.layout.item_hospital_list) { binding, data ->
            binding.item = data
        }
        mBinding.apply {
            lifecycleOwner = this@HospitalListActivity
            container.adapter = hospitalAdapter
            getList()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HospitalListActivity::class.java)
        }
    }
}
