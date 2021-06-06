package cn.hospital.registerplatform.ui.component.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.FragmentProfileBinding
import cn.hospital.registerplatform.ui.component.login.LoginActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val mBinding: FragmentProfileBinding by databind()
    private val mViewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@ProfileFragment
            username.setOnClickListener {
                startActivity(LoginActivity.newIntent(requireContext()))
            }
        }
    }
}
