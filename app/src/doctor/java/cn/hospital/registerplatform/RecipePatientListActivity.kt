package cn.hospital.registerplatform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import cn.hospital.registerplatform.data.dto.RecipeDoctorCombinedListItem
import cn.hospital.registerplatform.databinding.ActivityRecipePatientListBinding
import cn.hospital.registerplatform.databinding.ItemRecipeEditListBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.base.BaseListAdapter
import cn.hospital.registerplatform.ui.component.recipe.RecipeViewModel
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipePatientListActivity : ActionBarActivity("患者列表") {
    private val mBinding: ActivityRecipePatientListBinding by databind(R.layout.activity_recipe_patient_list)
    private val mViewModel: RecipeViewModel by viewModels()

    private lateinit var recipeAdapter: BaseListAdapter<RecipeDoctorCombinedListItem, ItemRecipeEditListBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeAdapter = BaseListAdapter(listOf(), R.layout.item_recipe_edit_list) { binding, data ->
            binding.userInfo = data.userInfo
            binding.info = data.recipeInfo
            binding.isFinish = data.hasRecipe
            if (data.hasRecipe) {
                binding.editButton.setOnClickListener {
                    startActivity(
                        EditRecipeAbstractActivity.newIntent(
                            this,
                            data.recipeId, data.recipeInfo.user, false
                        )
                    )
                }
                binding.detailButton.visibility = View.VISIBLE
                binding.detailButton.setOnClickListener {
                    startActivity(
                        RecipePatientDetailListActivity.newIntent(
                            this,
                            data.recipeInfo
                        )
                    )
                }
            } else {
                binding.detailButton.visibility = View.INVISIBLE
                binding.editButton.setText(R.string.recipe_submit_button)
                binding.editButton.setOnClickListener {
                    startActivity(
                        EditRecipeAbstractActivity.newIntent(
                            this,
                            data.recipeId, data.recipeInfo.user, true
                        )
                    )
                }
            }
        }

        mBinding.apply {
            lifecycleOwner = this@RecipePatientListActivity
            container.adapter = recipeAdapter
            mViewModel.getDoctorRecipeList().observe(this@RecipePatientListActivity) {
                recipeAdapter.updateList(it)
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RecipePatientListActivity::class.java)
        }
    }
}
