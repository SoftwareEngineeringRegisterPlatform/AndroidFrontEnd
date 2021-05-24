package cn.hospital.registerplatform.ui.component.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.FragmentHomeBinding
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mViewModel: MainViewModel by viewModels()
    private val mBinding: FragmentHomeBinding by databind()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            buttonContainer.layoutManager = GridLayoutManager(requireContext(), 2)
            buttonContainer.adapter = HomeAdapter(mViewModel.buttonList)
            mainViewModel = mViewModel
            lifecycleOwner = this@HomeFragment
        }
    }
}