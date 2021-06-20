package cn.hospital.registerplatform.ui.component.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.FragmentProfileBinding
import cn.hospital.registerplatform.ui.component.login.LoginActivity
import cn.hospital.registerplatform.ui.component.login.UploadInfoActivity
import cn.hospital.registerplatform.ui.component.recipe.RecipeListActivity
import cn.hospital.registerplatform.ui.component.recipe.RecipeListEditActivity
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
        mViewModel.requireLogin.observe(viewLifecycleOwner) {
            if (it) {
                mBinding.updateUserInfo.visibility = View.INVISIBLE
                mBinding.meaningless.visibility = View.INVISIBLE
            } else {
                mBinding.updateUserInfo.visibility = View.VISIBLE
                mBinding.meaningless.visibility = View.VISIBLE
            }
        }
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
                when (mViewModel.userInfo.value?.phoneNumber) {
                    "189123456789" -> startActivity(RecipeListEditActivity.newIntent(requireContext()))
                    else -> startActivity(RecipeListActivity.newIntent(requireContext()))
                }
            }
            buttonDoctor.setOnClickListener {
                ToastUtils.show(requireContext(), R.string.waiting_for_backend_complete)
            }
            buttonHospital.setOnClickListener {
                ToastUtils.show(requireContext(), R.string.waiting_for_backend_complete)
            }
            updateUserInfo.setOnClickListener {
                startActivity(UploadInfoActivity.newIntent(requireContext()))
            }
        }
    }
}
