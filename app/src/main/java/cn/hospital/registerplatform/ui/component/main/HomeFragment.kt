package cn.hospital.registerplatform.ui.component.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.FragmentHomeBinding
import cn.hospital.registerplatform.utils.ToastUtils
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mViewModel: MainViewModel by viewModels()
    private val mBinding: FragmentHomeBinding by databind()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            currentCitySelector.setOnClickListener {
                ToastUtils.show(requireContext(), R.string.waiting_for_backend_complete)
            }
            searchContainer.setOnClickListener {
                // startActivity(HospitalListActivity.newIntent(MainActivity))
//                it.context.apply {
//                    startActivity(HospitalListActivity.newIntent(this))
//                }
                MainActivity.onSearchHospital()
            }
            buttonContainer.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = HomeAdapter(mViewModel.buttonList)
            }
            mainViewModel = mViewModel
            lifecycleOwner = this@HomeFragment
        }
    }
}
