package cn.hospital.registerplatform.ui.component.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doFailure
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.DoctorInfo
import cn.hospital.registerplatform.data.dto.ScheduleInfo
import cn.hospital.registerplatform.databinding.ActivityRegisterScheduleBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.hospital.HospitalViewModel
import cn.hospital.registerplatform.utils.ToastUtils
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class RegisterScheduleActivity : ActionBarActivity("预约信息") {
    private val mBinding: ActivityRegisterScheduleBinding by databind(R.layout.activity_register_schedule)
    private val registerViewModel: RegisterViewModel by viewModels()
    private val hospitalViewModel: HospitalViewModel by viewModels()

    private var scheduleInfo by Delegates.notNull<ScheduleInfo>()
    private var doctorInfo = DoctorInfo.emptyDoctorInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleInfo = intent.getParcelableExtra(KEY_SCHEDULE_ID) ?: ScheduleInfo.emptyScheduleInfo()
        hospitalViewModel.getDoctorInfo(scheduleInfo.doctorId).observe(this) {
            it.doSuccess { doctorInfo ->
                this.doctorInfo = doctorInfo
                mBinding.doctorInfo = doctorInfo
                mBinding.executePendingBindings()
            }
        }
        mBinding.apply {
            scheduleInfo = this@RegisterScheduleActivity.scheduleInfo
            lifecycleOwner = this@RegisterScheduleActivity
            registerButton.setOnClickListener {
                registerViewModel.needLoginJump(this@RegisterScheduleActivity, lifecycleScope) {
                    registerViewModel.registerSchedule(this@RegisterScheduleActivity.scheduleInfo.id)
                        .observe(this@RegisterScheduleActivity) {
                            it.doSuccess {
                                startActivity(
                                    ScheduleResultActivity.newIntent(
                                        this@RegisterScheduleActivity,
                                        this@RegisterScheduleActivity.doctorInfo,
                                        this@RegisterScheduleActivity.scheduleInfo
                                    )
                                )
                            }
                            it.doFailure { exception ->
                                ToastUtils.show(this@RegisterScheduleActivity, "预约失败，原因：" + exception?.message)
                            }
                        }
                }
            }
            executePendingBindings()
        }
    }

    companion object {
        private const val KEY_SCHEDULE_ID = "key_schedule_id"
        fun newIntent(context: Context, scheduleId: ScheduleInfo): Intent {
            return Intent(context, RegisterScheduleActivity::class.java).apply {
                putExtra(KEY_SCHEDULE_ID, scheduleId)
            }
        }
    }
}
