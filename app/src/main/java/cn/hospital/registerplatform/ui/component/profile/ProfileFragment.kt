package cn.hospital.registerplatform.ui.component.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.FragmentProfileBinding
import com.hi.dhl.binding.databind

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val mBinding: FragmentProfileBinding by databind()
    private val mViewModel: ProfileViewModel by viewModels()
}