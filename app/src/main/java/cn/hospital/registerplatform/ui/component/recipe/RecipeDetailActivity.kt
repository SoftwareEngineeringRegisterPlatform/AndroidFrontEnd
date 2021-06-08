package cn.hospital.registerplatform.ui.component.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.RecipeDetailCombinedListItem
import cn.hospital.registerplatform.data.dto.RecipeInfo
import cn.hospital.registerplatform.databinding.ActivityRecipeDetailBinding
import cn.hospital.registerplatform.databinding.ItemRecipeDetailListBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeDetailActivity : ActionBarActivity("病历详情") {
    private val mBinding: ActivityRecipeDetailBinding by databind(R.layout.activity_recipe_detail)
    private val mViewModel: RecipeViewModel by viewModels()

    private lateinit var recipeAdapter: RecipeListAdapter<RecipeDetailCombinedListItem, ItemRecipeDetailListBinding>

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            val recipeInfo = intent.getParcelableExtra<RecipeInfo>(KEY_RECIPE_ID)
            if (recipeInfo != null) {
                recipeAdapter.updateList(mViewModel.getDetailInfoList(recipeInfo.exam_result, recipeInfo.prescription))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeAdapter = RecipeListAdapter(R.layout.item_recipe_detail_list) { binding, data ->
            binding.date = data.date
            binding.content = if(data.isExam) data.examInfo?.diag else data.prescriptionInfo?.medicine
            binding.type = if (data.isExam) "检查结果" else "处方"
        }

        mBinding.apply {
            lifecycleOwner = this@RecipeDetailActivity
            container.adapter = recipeAdapter
            getList()
        }
    }

    companion object {
        private const val KEY_RECIPE_ID = "key_recipe_id"
        fun newIntent(context: Context, recipeInfo: RecipeInfo): Intent {
            return Intent(context, RecipeDetailActivity::class.java).apply {
                putExtra(KEY_RECIPE_ID, recipeInfo)
            }
        }
    }
}