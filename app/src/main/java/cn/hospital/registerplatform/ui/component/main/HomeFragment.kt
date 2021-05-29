package cn.hospital.registerplatform.ui.component.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mViewModel: MainViewModel by viewModels()
    private val mBinding: FragmentHomeBinding by databind()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            root.setOnClickListener {
                activity?.findViewById<ExtendedFloatingActionButton>(R.id.fab)?.shrink()
            }
            buttonContainer.layoutManager = GridLayoutManager(requireContext(), 2)
            buttonContainer.adapter = HomeAdapter(mViewModel.buttonList)
            buttonContainer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    activity?.findViewById<ExtendedFloatingActionButton>(R.id.fab)?.shrink()
                }
            })
            mainViewModel = mViewModel
            lifecycleOwner = this@HomeFragment
        }
    }
}
