package cn.hospital.registerplatform.ui.component.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.ActivityHospitalDetailBinding
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepartmentListActivity : AppCompatActivity() {
    private val mBinding: ActivityHospitalDetailBinding by databind(R.layout.activity_department_list)
    private val mViewModel: HospitalViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
