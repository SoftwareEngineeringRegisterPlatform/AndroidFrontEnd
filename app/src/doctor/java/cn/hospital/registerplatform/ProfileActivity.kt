package cn.hospital.registerplatform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import cn.hospital.registerplatform.databinding.ActivityProfileBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import cn.hospital.registerplatform.ui.component.profile.ProfileFragment
import cn.hospital.registerplatform.ui.component.profile.ProfileViewModel
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    private val mBinding: ActivityProfileBinding by databind(R.layout.activity_profile)
    private val mViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.isDoctor = true
        mBinding.apply {
            ProfileFragment.addFragment(
                supportFragmentManager,
                R.id.profile_fragment_container
            )
        }
    }

    companion object {
        fun newClearIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }
}
