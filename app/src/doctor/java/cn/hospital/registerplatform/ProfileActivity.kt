package cn.hospital.registerplatform

import android.os.Bundle
import cn.hospital.registerplatform.databinding.ActivityProfileBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import cn.hospital.registerplatform.ui.component.profile.ProfileFragment
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    private val mBinding: ActivityProfileBinding by databind(R.layout.activity_profile)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            ProfileFragment.addFragment(
                supportFragmentManager,
                R.id.profile_fragment_container,
                true
            )
        }
    }
}
