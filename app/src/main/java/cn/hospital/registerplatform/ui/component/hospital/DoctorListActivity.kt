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
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.base.BaseListAdapter
import cn.hospital.registerplatform.utils.CombinedLiveData
import cn.hospital.registerplatform.utils.ToastUtils
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


@AndroidEntryPoint
class DoctorListActivity : ActionBarActivity("医生列表") {
    private val mBinding: ActivityDoctorListBinding by databind(R.layout.activity_doctor_list)
    private val mViewModel: HospitalViewModel by viewModels()

    private var departmentId by Delegates.notNull<Int>()

    private lateinit var dateAdapter: BaseListAdapter<String, ItemScheduleDateBinding>
    private lateinit var doctorAdapter: BaseListAdapter<DoctorListItem, ItemDoctorListBinding>

    private lateinit var doctorList: List<DoctorListItem>
    private lateinit var scheduleMap: Map<String, List<ScheduleInfo>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        departmentId = intent.getIntExtra(KEY_DEPARTMENT_ID, 0)
        dateAdapter = BaseListAdapter(
            listOf(),
            R.layout.item_schedule_date,
        ) { binding, data ->
            binding.date = data
            binding.onClick = View.OnClickListener {
                val thisDayDoctor = scheduleMap[data]?.map { it.doctorName }
                doctorAdapter.updateList(doctorList.filter {
                    thisDayDoctor?.contains(it.name) ?: false
                })
            }
        }
        doctorAdapter = BaseListAdapter(
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
        CombinedLiveData(
            mViewModel.getAllDoctorList(departmentId),
            mViewModel.getDepartmentScheduleList(departmentId)
        ).observe(this) {
            if (it.first != null && it.second != null) {
                it.first!!.doSuccess { list ->
                    this@DoctorListActivity.doctorList = list
                    if (list.size > 0) {
                        it.second!!.doSuccess { map ->
                            val dateList = map.keys.sorted().toList()
                            if (dateList.size > 0) {
                                dateAdapter.updateList(dateList)
                                scheduleMap = map
                                doctorAdapter.updateList(list.filter { doctorListItem ->
                                    map[dateList.getOrElse(0) { "" }]?.map { scheduleInfo -> scheduleInfo.doctorName }
                                        ?.contains(doctorListItem.name) ?: false
                                })
                            } else {
                                ToastUtils.show(this@DoctorListActivity, "No doctor Available.")
                            }
                        }
                    } else {
                        ToastUtils.show(this@DoctorListActivity, "No doctor in this department.")
                    }
                }
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
