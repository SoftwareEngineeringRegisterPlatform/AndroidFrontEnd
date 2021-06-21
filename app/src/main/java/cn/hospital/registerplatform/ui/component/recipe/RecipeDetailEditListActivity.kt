package cn.hospital.registerplatform.ui.component.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.RecipeDetailCombinedListItem
import cn.hospital.registerplatform.data.dto.RecipeInfo
import cn.hospital.registerplatform.databinding.ActivityRecipeDetailEditBinding
import cn.hospital.registerplatform.databinding.ItemRecipeDetailEditListBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.hospital.HospitalListAdapter
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.properties.Delegates

@AndroidEntryPoint
class RecipeDetailEditListActivity : ActionBarActivity("病历详情") {
    private val mBinding: ActivityRecipeDetailEditBinding by databind(R.layout.activity_recipe_detail_edit)
    private val mViewModel: RecipeViewModel by viewModels()

    private lateinit var recipeAdapter: HospitalListAdapter<RecipeDetailCombinedListItem, ItemRecipeDetailEditListBinding>
    private var recipeInfo by Delegates.notNull<RecipeInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeInfo = intent.getParcelableExtra(KEY_RECIPE_ID)!!
        recipeAdapter = HospitalListAdapter(listOf(), R.layout.item_recipe_detail_edit_list) { binding, data ->
            binding.date = data.date
            binding.content = data.examInfo?.content
            binding.type = data.examInfo?.diag
            binding.button.setOnClickListener {
                startActivity(EditRecipeDetailActivity.newIntent(this, data, 0, 0, false))
            }
        }

        mBinding.apply {
            lifecycleOwner = this@RecipeDetailEditListActivity
            container.adapter = recipeAdapter
            mViewModel.getDetailInfoList(recipeInfo.exam_result, recipeInfo.prescription)
                .observe(this@RecipeDetailEditListActivity) {
                    recipeAdapter.updateList(it)
                }
        }
        mBinding.addExamButton.setOnClickListener {
            startActivity(EditRecipeDetailActivity.newIntent(this, RecipeDetailCombinedListItem(0, 0, Date(), false, null, null), recipeInfo.regist, recipeInfo.user, true))
        }
    }

    companion object {
        private const val KEY_RECIPE_ID = "key_recipe_id"
        fun newIntent(context: Context, recipeInfo: RecipeInfo): Intent {
            return Intent(context, RecipeDetailEditListActivity::class.java).apply {
                putExtra(KEY_RECIPE_ID, recipeInfo)
            }
        }
    }
}
