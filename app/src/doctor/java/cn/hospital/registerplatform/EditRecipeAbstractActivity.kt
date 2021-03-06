package cn.hospital.registerplatform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.api.doFailure
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.databinding.ActivityRecipeAbstractEditBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.recipe.RecipeViewModel
import cn.hospital.registerplatform.utils.ToastUtils
import cn.hospital.registerplatform.utils.delayLaunch
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class EditRecipeAbstractActivity : ActionBarActivity("医嘱提交") {
    private val mBinding: ActivityRecipeAbstractEditBinding by databind(R.layout.activity_recipe_abstract_edit)
    private val mViewModel: RecipeViewModel by viewModels()

    private var recipeId by Delegates.notNull<Int>()
    private var userId by Delegates.notNull<Int>()
    private var isSubmit by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeId = intent.getIntExtra(KEY_RECIPE_ID, 0)
        userId = intent.getIntExtra(KEY_USER_ID, 0)
        isSubmit = intent.getBooleanExtra(KEY_IS_SUBMIT, false)
        mBinding.apply {
            lifecycleOwner = this@EditRecipeAbstractActivity
            submitAbstract.setOnClickListener {
                (if (isSubmit) mViewModel.submitRecipeInfo(
                    userId,
                    recipeId,
                    recipeDiag.text.toString(),
                    recipeSuggestion.text.toString()
                ) else mViewModel.editRecipeInfo(
                    recipeId,
                    recipeDiag.text.toString(),
                    recipeSuggestion.text.toString()
                )).observe(this@EditRecipeAbstractActivity) {
                    it.doSuccess {
                        ToastUtils.show(this@EditRecipeAbstractActivity, "${if (isSubmit) "上传" else "修改"}医嘱成功")
                        lifecycleScope.delayLaunch {
                            finish()
                        }
                    }
                    it.doFailure {
                        ToastUtils.show(this@EditRecipeAbstractActivity, "${if (isSubmit) "上传" else "修改"}医嘱失败")

                    }
                }
            }
        }
    }

    companion object {
        private const val KEY_RECIPE_ID = "key_recipe_id"
        private const val KEY_USER_ID = "key_user_id"
        private const val KEY_IS_SUBMIT = "key_is_submit"
        fun newIntent(context: Context, recipeId: Int, userId: Int, isSubmit: Boolean): Intent {
            return Intent(context, EditRecipeAbstractActivity::class.java).apply {
                putExtra(KEY_RECIPE_ID, recipeId)
                putExtra(KEY_USER_ID, userId)
                putExtra(KEY_IS_SUBMIT, isSubmit)
            }
        }
    }
}
