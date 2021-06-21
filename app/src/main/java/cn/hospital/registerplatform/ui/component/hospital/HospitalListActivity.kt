package cn.hospital.registerplatform.ui.component.hospital

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.HospitalListItem
import cn.hospital.registerplatform.databinding.ActivityHospitalListBinding
import cn.hospital.registerplatform.databinding.ItemHospitalListBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.utils.ToastUtils
import cn.hospital.registerplatform.utils.afterTextChanged
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

@AndroidEntryPoint
class HospitalListActivity : ActionBarActivity("医院列表") {
    private val mBinding: ActivityHospitalListBinding by databind(R.layout.activity_hospital_list)
    private val mViewModel: HospitalViewModel by viewModels()
    private lateinit var conditionArray: Array<String>
    private lateinit var hospitalAdapter: HospitalPagingAdapter<HospitalListItem, ItemHospitalListBinding>

    private var searchPosition = 0;
    private var searchName = "";

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getHospitalList().collect {
                hospitalAdapter.submitData(it)
            }
        }
    }

    private fun getList(name: String, type: String) {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getHospitalFilterList(name, type).collect {
                hospitalAdapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        conditionArray = resources.getStringArray(R.array.hospital_search_condition)
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
            container.adapter = hospitalAdapter
            getList()
            hospitalSearchSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    searchPosition = position
                    getList(searchName, conditionArray[position])
//                    ToastUtils.show(this@HospitalListActivity, conditionArray[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            hospitalSearchName.afterTextChanged { text ->
                searchName = text
                getList(text, conditionArray[searchPosition])
            }

        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HospitalListActivity::class.java)
        }
    }
}
