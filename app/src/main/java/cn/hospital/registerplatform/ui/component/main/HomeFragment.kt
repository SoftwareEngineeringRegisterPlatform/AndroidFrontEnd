package cn.hospital.registerplatform.ui.component.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.FragmentHomeBinding
import com.hi.dhl.binding.databind

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mViewModel: MainViewModel by viewModels()
    private val mBinding: FragmentHomeBinding by databind()

}