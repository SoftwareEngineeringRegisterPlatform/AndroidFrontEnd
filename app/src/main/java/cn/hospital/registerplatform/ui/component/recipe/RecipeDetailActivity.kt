package cn.hospital.registerplatform.ui.component.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.ActivityRecipeDetailBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import coil.load
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailActivity : ActionBarActivity("病历详情") {
    private val mBinding: ActivityRecipeDetailBinding by databind(R.layout.activity_recipe_detail)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@RecipeDetailActivity
            contentContainer.load(R.mipmap.content)
            bottomButtons.load(R.mipmap.tailer)
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RecipeDetailActivity::class.java)
        }
    }
}
