package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.DoctorListItem
import cn.hospital.registerplatform.data.dto.ScheduleInfo
import cn.hospital.registerplatform.databinding.ActivityDoctorListBinding
import cn.hospital.registerplatform.databinding.ItemDoctorListBinding
import cn.hospital.registerplatform.databinding.ItemScheduleDateBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DoctorListActivity : BaseActivity() {
    private val mBinding: ActivityDoctorListBinding by databind(R.layout.activity_doctor_list)
    private val mViewModel: HospitalViewModel by viewModels()

    private var departmentId by Delegates.notNull<Int>()

    private lateinit var dateAdapter: HospitalListAdapter<String, ItemScheduleDateBinding>
    private lateinit var doctorAdapter: HospitalListAdapter<DoctorListItem, ItemDoctorListBinding>

    private lateinit var doctorList: List<DoctorListItem>
    private lateinit var scheduleMap: Map<String, List<ScheduleInfo>>
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "医生列表"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        departmentId = intent.getIntExtra(KEY_DEPARTMENT_ID, 0)
        dateAdapter = HospitalListAdapter(
            listOf(),
            R.layout.item_schedule_date,
        ) { binding, data ->
            binding.date = data
            binding.onClick = View.OnClickListener {
                val thisDayDoctor = scheduleMap[data]?.map { it.doctor__name }
                doctorAdapter.updateList(doctorList.filter {
                    thisDayDoctor?.contains(it.name) ?: false
                })
            }
        }
        doctorAdapter = HospitalListAdapter(
            listOf(),
            R.layout.item_doctor_list,
        ) { binding, data ->
            binding.item = data
            binding.onClick = View.OnClickListener {
                startActivity(DoctorDetailActivity.newIntent(this, data.id))
            }
        }
        mBinding.apply {
            lifecycleOwner = this@DoctorListActivity
            dateContainer.layoutManager =
                LinearLayoutManager(this@DoctorListActivity, LinearLayoutManager.HORIZONTAL, false)
            dateContainer.adapter = dateAdapter
            container.adapter = doctorAdapter
        }
        mViewModel.getAllDoctorList(departmentId).observe(this@DoctorListActivity) {
            it.doSuccess { list ->
                this@DoctorListActivity.doctorList = list
            }
        }
        mViewModel.getDepartmentScheduleList(departmentId).observe(this@DoctorListActivity) {
            it.doSuccess { map ->
                val dateList = map.keys.sorted().toList()
                dateAdapter.updateList(dateList)
                scheduleMap = map
                doctorAdapter.updateList(doctorList.filter { doctorListItem ->
                    scheduleMap[dateList.getOrElse(0) { "" }]?.map { scheduleInfo -> scheduleInfo.doctor__name }
                        ?.contains(doctorListItem.name) ?: false
                })
            }
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
