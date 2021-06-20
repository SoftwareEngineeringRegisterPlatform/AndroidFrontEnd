package cn.hospital.registerplatform.ui.component.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.DoctorInfo
import cn.hospital.registerplatform.data.dto.ScheduleInfo
import cn.hospital.registerplatform.databinding.ActivityScheduleResultBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.hospital.HospitalViewModel
import cn.hospital.registerplatform.ui.component.main.MainActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ScheduleResultActivity : ActionBarActivity("预约成功") {
    private val mBinding: ActivityScheduleResultBinding by databind(R.layout.activity_schedule_result)

    private var doctorInfo by Delegates.notNull<DoctorInfo>()
    private var scheduleInfo by Delegates.notNull<ScheduleInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doctorInfo = intent.getParcelableExtra(KEY_DOCTOR_INFO) ?: DoctorInfo.emptyDoctorInfo()
        scheduleInfo = intent.getParcelableExtra(KEY_SCHEDULE_INFO) ?: ScheduleInfo.emptyScheduleInfo()
        mBinding.apply {
            doctorInfo = this@ScheduleResultActivity.doctorInfo
            scheduleInfo = this@ScheduleResultActivity.scheduleInfo
            lifecycleOwner = this@ScheduleResultActivity

            rtbButton.setOnClickListener {
                startActivity(MainActivity.newClearIntent(this@ScheduleResultActivity))
            }
            executePendingBindings()
        }
    }

    companion object {
        private const val KEY_DOCTOR_INFO = "key_doctor_info"
        private const val KEY_SCHEDULE_INFO = "key_schedule_info"
        fun newIntent(context: Context, doctorInfo: DoctorInfo, scheduleInfo: ScheduleInfo): Intent {
            return Intent(context, ScheduleResultActivity::class.java).apply {
                putExtra(KEY_DOCTOR_INFO, doctorInfo)
                putExtra(KEY_SCHEDULE_INFO, scheduleInfo)
            }
        }
    }
}