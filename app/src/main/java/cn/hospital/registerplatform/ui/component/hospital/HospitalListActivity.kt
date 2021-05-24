package cn.hospital.registerplatform.ui.component.hospital

import android.os.Bundle
import androidx.activity.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.ActivityHospitalDetailBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalListActivity : BaseActivity() {
    private val mBinding: ActivityHospitalDetailBinding by databind(R.layout.activity_hospital_detail)
    private val mViewModel: HospitalViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_list)
    }
}