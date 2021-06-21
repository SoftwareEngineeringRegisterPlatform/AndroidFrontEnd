package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.HospitalFilter
import cn.hospital.registerplatform.data.dto.HospitalListItem
import cn.hospital.registerplatform.data.dto.HospitalSearchCondition
import cn.hospital.registerplatform.databinding.ActivityHospitalListBinding
import cn.hospital.registerplatform.databinding.ItemHospitalListBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import cn.hospital.registerplatform.utils.afterTextChanged
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HospitalListActivity : BaseActivity() {
    private val mBinding: ActivityHospitalListBinding by databind(R.layout.activity_hospital_list)
    private val mViewModel: HospitalViewModel by viewModels()
    private lateinit var hospitalAdapter: HospitalPagingAdapter<HospitalListItem, ItemHospitalListBinding>

    private var hospitalFilter = HospitalFilter.fromData("", 0)

    private var getListJob: Job? = null

    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getHospitalFilterList(hospitalFilter.toJsonString(this@HospitalListActivity)).collect {
                hospitalAdapter.submitData(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hospitalAdapter = HospitalPagingAdapter(R.layout.item_hospital_list) { binding, data ->
            binding.item = data
            binding.onClick = View.OnClickListener {
                startActivity(
                    HospitalDetailActivity.newIntent(
                        this@HospitalListActivity,
                        data.id
                    )
                )
            }
        }
        mBinding.apply {
            lifecycleOwner = this@HospitalListActivity
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            container.adapter = hospitalAdapter
            getList()
            hospitalSearchSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    hospitalFilter.type = HospitalSearchCondition.fromIndex(position)
                    getList()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    parent?.setSelection(0)
                }
            }
            hospitalSearchName.afterTextChanged { text ->
                hospitalFilter.name = text
                getList()
            }

        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HospitalListActivity::class.java)
        }
    }
}
