package cn.hospital.registerplatform.ui.component.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.RecipeDoctorCombinedListItem
import cn.hospital.registerplatform.databinding.ActivityRecipeEditListBinding
import cn.hospital.registerplatform.databinding.ItemRecipeEditListBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.hospital.HospitalListAdapter
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class RecipeListEditActivity : ActionBarActivity("芜湖，起飞！") {
    private val mBinding: ActivityRecipeEditListBinding by databind(R.layout.activity_recipe_edit_list)
    private val mViewModel: RecipeViewModel by viewModels()

    private lateinit var recipeAdapter: HospitalListAdapter<RecipeDoctorCombinedListItem, ItemRecipeEditListBinding>

    private var getListJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeAdapter = HospitalListAdapter(listOf(), R.layout.item_recipe_edit_list) { binding, data ->
            binding.userInfo = data.userInfo
            binding.info = data.recipeInfo
            if (data.hasRecipe) {
                binding.editButton.setOnClickListener {
                    startActivity(data.recipeInfo?.let { it1 ->
                        EditRecipeAbstractActivity.newIntent(this,
                            it1
                        )
                    })
                }
                binding.detailButton.setOnClickListener {
                    startActivity(data.recipeInfo?.let { it1 ->
                        RecipeDetailEditListActivity.newIntent(
                            this,
                            it1
                        )
                    })
                }
            }
        }

        mBinding.apply {
            lifecycleOwner = this@RecipeListEditActivity
            container.adapter = recipeAdapter
            mViewModel.getDoctorRecipeList().observe(this@RecipeListEditActivity) {
                recipeAdapter.updateList(it)
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RecipeListEditActivity::class.java)
        }
    }
}
