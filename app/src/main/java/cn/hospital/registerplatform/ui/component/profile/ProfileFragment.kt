package cn.hospital.registerplatform.ui.component.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.FragmentProfileBinding
import cn.hospital.registerplatform.ui.component.login.LoginActivity
import cn.hospital.registerplatform.ui.component.register.RegisterListActivity
import cn.hospital.registerplatform.utils.ToastUtils
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val mBinding: FragmentProfileBinding by databind()
    private val mViewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            profileViewModel = mViewModel
            lifecycleOwner = this@ProfileFragment
            username.setOnClickListener {
                if (mViewModel.requireLogin.value == true) {
                    startActivity(LoginActivity.newIntent(requireContext()))
                }
            }
            mViewModel.fetchUserInfo()
            toolbarProfile.overflowIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_settings_24)
            toolbarProfile.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_update_user_info -> {
                        true
                    }
                    R.id.action_logout -> {
                        mViewModel.clearToken()
                        mViewModel.fetchUserInfo()
                        true
                    }
                    else -> false
                }
            }
            buttonRegister.setOnClickListener {
                startActivity(RegisterListActivity.newIntent(requireContext()))
            }
            buttonAdvisory.setOnClickListener {
                ToastUtils.show(requireContext(), "等待后端API完善")
            }
            buttonDoctor.setOnClickListener {
                ToastUtils.show(requireContext(), "等待后端API完善")
            }
            buttonHospital.setOnClickListener {
                ToastUtils.show(requireContext(), "等待后端API完善")
            }
        }
    }
}
