package cn.hospital.registerplatform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.api.doFailure
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.RecipeDetailCombinedListItem
import cn.hospital.registerplatform.databinding.ActivityRecipeAbstractEditBinding
import cn.hospital.registerplatform.ui.base.ActionBarActivity
import cn.hospital.registerplatform.ui.component.recipe.RecipeViewModel
import cn.hospital.registerplatform.utils.ToastUtils
import cn.hospital.registerplatform.utils.delayLaunch
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class EditRecipeDetailActivity : ActionBarActivity("病历提交") {
    private val mBinding: ActivityRecipeAbstractEditBinding by databind(R.layout.activity_recipe_abstract_edit)
    private val mViewModel: RecipeViewModel by viewModels()

    private var recipeDetailItem by Delegates.notNull<RecipeDetailCombinedListItem>()
    private var registId by Delegates.notNull<Int>()
    private var userId by Delegates.notNull<Int>()
    private var isSubmit by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeDetailItem = intent.getParcelableExtra(KEY_RECIPE_DETAIL)!!
        registId = intent.getIntExtra(KEY_REGIST_ID, 0)
        userId = intent.getIntExtra(KEY_USER_ID, 0)
        isSubmit = intent.getBooleanExtra(KEY_IS_SUBMIT, false)

        mBinding.apply {
            lifecycleOwner = this@EditRecipeDetailActivity
            recipeDiagPrompt.text = "病历条目类型"
            recipeDiag.hint = "填写病历条目类型"
            recipeSuggestionPrompt.text = "病历条目内容"
            recipeSuggestion.hint = "填写病历条目内容"
            recipeDiag.setText(recipeDetailItem.examInfo?.diag)
            recipeSuggestion.setText(recipeDetailItem.examInfo?.content)

            submitAbstract.setOnClickListener {
                (if (isSubmit) mViewModel.submitExamInfo(
                    userId,
                    registId,
                    recipeDiag.text.toString(),
                    recipeSuggestion.text.toString()
                ) else mViewModel.editExamInfo(
                    recipeDetailItem.examId,
                    recipeDiag.text.toString(),
                    recipeSuggestion.text.toString()
                )).observe(this@EditRecipeDetailActivity) {
                    it.doSuccess {
                        ToastUtils.show(this@EditRecipeDetailActivity, "${if (isSubmit) "上传" else "修改"}病历成功")
                        lifecycleScope.delayLaunch {
                            finish()
                        }
                    }
                    it.doFailure {
                        ToastUtils.show(this@EditRecipeDetailActivity, "${if (isSubmit) "上传" else "修改"}病历失败")

                    }
                }
            }
        }
    }

    companion object {
        private const val KEY_RECIPE_DETAIL = "key_recipe_detail"
        private const val KEY_REGIST_ID = "key_regist_id"
        private const val KEY_USER_ID = "key_user_id"
        private const val KEY_IS_SUBMIT = "key_is_submit"
        fun newIntent(
            context: Context,
            recipeDetail: RecipeDetailCombinedListItem,
            registId: Int,
            userId: Int,
            isSubmit: Boolean
        ): Intent {
            return Intent(context, EditRecipeDetailActivity::class.java).apply {
                putExtra(KEY_RECIPE_DETAIL, recipeDetail)
                putExtra(KEY_REGIST_ID, registId)
                putExtra(KEY_USER_ID, userId)
                putExtra(KEY_IS_SUBMIT, isSubmit)
            }
        }
    }
}
