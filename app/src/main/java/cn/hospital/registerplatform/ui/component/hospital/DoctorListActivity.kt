package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.DoctorListItem
import cn.hospital.registerplatform.databinding.ActivityDoctorListBinding
import cn.hospital.registerplatform.databinding.ItemDoctorListBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DoctorListActivity : BaseActivity() {
    private val mBinding: ActivityDoctorListBinding by databind(R.layout.activity_doctor_list)
    private val mViewModel: HospitalViewModel by viewModels()

    private var departmentId by Delegates.notNull<Int>()

    private lateinit var doctorAdapter: HospitalListAdapter<DoctorListItem, ItemDoctorListBinding>

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getDoctorList(departmentId).collect {
                doctorAdapter.submitData(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "科室列表"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        departmentId = intent.getIntExtra(KEY_DEPARTMENT_ID, 0)
        doctorAdapter = HospitalListAdapter(R.layout.item_doctor_list) { binding, data ->
            binding.item = data
            binding.onClick = View.OnClickListener {

            }
        }
        mBinding.apply {
            lifecycleOwner = this@DoctorListActivity
            container.adapter = doctorAdapter
            getList()
        }
    }

    companion object {
        private const val KEY_DEPARTMENT_ID = "key_department_id"
        fun newIntent(context: Context, departmentId: Int): Intent {
            return Intent(context, DoctorListActivity::class.java).apply {
                putExtra(KEY_DEPARTMENT_ID, departmentId)
            }
        }
    }
}
