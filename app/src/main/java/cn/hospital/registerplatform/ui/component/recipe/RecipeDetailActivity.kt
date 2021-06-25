package cn.hospital.registerplatform.ui.component.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.RecipeDetailCombinedListItem
import cn.hospital.registerplatform.data.dto.RecipeInfo
import cn.hospital.registerplatform.databinding.ActivityRecipeDetailBinding
import cn.hospital.registerplatform.databinding.ItemRecipeDetailListBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.base.BaseListAdapter
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class RecipeDetailActivity : ActionBarActivity("病历详情") {
    private val mBinding: ActivityRecipeDetailBinding by databind(R.layout.activity_recipe_detail)
    private val mViewModel: RecipeViewModel by viewModels()

    private lateinit var recipeAdapter: BaseListAdapter<RecipeDetailCombinedListItem, ItemRecipeDetailListBinding>
    private var recipeInfo by Delegates.notNull<RecipeInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeInfo = intent.getParcelableExtra(KEY_RECIPE_ID)!!
        recipeAdapter = BaseListAdapter(listOf(), R.layout.item_recipe_detail_list) { binding, data ->
            binding.date = data.date
            binding.content = data.examInfo?.content
            binding.type = data.examInfo?.diag

        }

        mBinding.apply {
            lifecycleOwner = this@RecipeDetailActivity
            container.adapter = recipeAdapter
            mViewModel.getDetailInfoList(recipeInfo.exam_result, recipeInfo.prescription)
                .observe(this@RecipeDetailActivity) {
                    recipeAdapter.updateList(it)
                }
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
