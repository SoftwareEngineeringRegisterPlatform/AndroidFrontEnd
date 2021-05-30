package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.DepartmentListItem
import cn.hospital.registerplatform.databinding.ActivityDepartmentListBinding
import cn.hospital.registerplatform.databinding.ItemDepartmentListBinding
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DepartmentListActivity : AppCompatActivity() {
    private val mBinding: ActivityDepartmentListBinding by databind(R.layout.activity_department_list)
    private val mViewModel: HospitalViewModel by viewModels()
    private var hospitalId by Delegates.notNull<Int>()

    private lateinit var departmentAdapter: HospitalPagingAdapter<DepartmentListItem, ItemDepartmentListBinding>

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getDepartmentList(hospitalId).collect {
                departmentAdapter.submitData(it)
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
        hospitalId = intent.getIntExtra(KEY_HOSPITAL_ID, 0)
        departmentAdapter = HospitalPagingAdapter(R.layout.item_department_list) { binding, data ->
            binding.item = data
            binding.onClick = View.OnClickListener {
                startActivity(DoctorListActivity.newIntent(this, data.id))
            }
        }
        mBinding.apply {
            lifecycleOwner = this@DepartmentListActivity
            container.adapter = departmentAdapter
            getList()
        }
    }

    companion object {
        private const val KEY_HOSPITAL_ID = "key_hospital_id"
        fun newIntent(context: Context, hospitalId: Int): Intent {
            return Intent(context, DepartmentListActivity::class.java).apply {
                putExtra(KEY_HOSPITAL_ID, hospitalId)
            }
        }
    }
}
