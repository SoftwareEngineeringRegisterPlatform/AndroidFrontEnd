package cn.hospital.registerplatform.ui.component.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.FragmentProfileBinding
import cn.hospital.registerplatform.databinding.ItemMainButtonBinding
import cn.hospital.registerplatform.databinding.ItemProfileButtonBinding
import cn.hospital.registerplatform.ui.base.BaseListAdapter
import cn.hospital.registerplatform.ui.component.login.LoginActivity
import cn.hospital.registerplatform.ui.component.login.UploadInfoActivity
import cn.hospital.registerplatform.ui.component.main.HomeCardData
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val mBinding: FragmentProfileBinding by databind()
    private val mViewModel: ProfileViewModel by viewModels()

    private var isDoctor by Delegates.notNull<Boolean>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isDoctor = arguments?.getBoolean(KEY_IS_DOCTOR) ?: false
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
            userIdentity.text = getString(if (isDoctor) R.string.identity_doctor else R.string.normal_register)
            profileButtonContainer.apply {
                layoutManager = GridLayoutManager(requireContext(), if (isDoctor) 3 else 4)
                adapter = BaseListAdapter<HomeCardData, ItemProfileButtonBinding>(
                    if (isDoctor) mViewModel.doctorButtonList else mViewModel.patientButtonList,
                    R.layout.item_profile_button,
                ) { binding, data ->
                    binding.item = data
                }
            }
            updateUserInfo.setOnClickListener {
                startActivity(UploadInfoActivity.newIntent(requireContext()))
            }
        }
    }

    companion object {
        private const val KEY_IS_DOCTOR = "key_is_doctor"
        fun addFragment(
            manager: FragmentManager,
            fragmentContainerId: Int,
            isDoctor: Boolean = false
        ) {
            manager.commit {
                replace(fragmentContainerId, ProfileFragment::class.java, bundleOf(KEY_IS_DOCTOR to isDoctor))
            }
        }
    }
}
