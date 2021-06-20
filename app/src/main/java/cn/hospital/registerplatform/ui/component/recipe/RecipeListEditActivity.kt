package cn.hospital.registerplatform.ui.component.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.dto.RecipeCombinedListItem
import cn.hospital.registerplatform.databinding.ActivityRecipeEditListBinding
import cn.hospital.registerplatform.databinding.ItemRecipeEditListBinding
import cn.hospital.registerplatform.databinding.ItemRecipeListBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.hospital.DoctorDetailActivity
import cn.hospital.registerplatform.ui.component.hospital.HospitalPagingAdapter
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeListEditActivity : ActionBarActivity("芜湖，起飞！") {
    private val mBinding: ActivityRecipeEditListBinding by databind(R.layout.activity_recipe_edit_list)
    private val mViewModel: RecipeViewModel by viewModels()

    private lateinit var recipeAdapter: HospitalPagingAdapter<RecipeCombinedListItem, ItemRecipeEditListBinding>

    private var getListJob: Job? = null
    private fun getList() {
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            mViewModel.getRecipeList().collectLatest {
                recipeAdapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeAdapter = HospitalPagingAdapter(R.layout.item_recipe_edit_list) { binding, data ->
            binding.doctorInfo = data.doctorInfo
            binding.info = data.recipeInfo
            binding.editButton.setOnClickListener {
                startActivity(EditRecipeAbstractActivity.newIntent(this, data.recipeInfo.regist))
            }
            binding.detailButton.setOnClickListener {
                startActivity(RecipeDetailActivity.newIntent(this, data.recipeInfo))
            }
        }

        mBinding.apply {
            lifecycleOwner = this@RecipeListEditActivity
            container.adapter = recipeAdapter
            getList()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RecipeListEditActivity::class.java)
        }
    }
}
