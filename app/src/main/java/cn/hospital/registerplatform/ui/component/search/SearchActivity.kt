package cn.search.registerplatform.ui.component.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.search.registerplatform.R
import cn.search.registerplatform.data.dto.searchListItem
import cn.search.registerplatform.databinding.ActivitysearchListBinding
import cn.search.registerplatform.databinding.ItemsearchListBinding
import cn.search.registerplatform.ui.base.ActionBarActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class searchListActivity : ActionBarActivity("搜索") {
    private val mBinding: ActivitysearchListBinding by databind(R.layout.activity_search_list)
    private val mViewModel: searchViewModel by viewModels()

    private lateinit var searchAdapter: searchPagingAdapter<searchListItem, ItemsearchListBinding>

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getsearchList().collect {
                searchAdapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchAdapter = searchPagingAdapter(R.layout.item_search_list) { binding, data ->
            binding.item = data
            binding.onClick = View.OnClickListener {
                startActivity(
                    searchDetailActivity.newIntent(
                        this@searchListActivity,
                        data.id
                    )
                )
            }
        }
        mBinding.apply {
            lifecycleOwner = this@searchListActivity
            container.adapter = searchAdapter
            getList()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, searchListActivity::class.java)
        }
    }
}
