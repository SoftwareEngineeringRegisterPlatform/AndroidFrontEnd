package cn.hospital.registerplatform.ui.component.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doFailure
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.databinding.ActivitySubmitRecipeAbstractBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import cn.hospital.registerplatform.ui.component.main.MainActivity
import cn.hospital.registerplatform.utils.ToastUtils
import cn.hospital.registerplatform.utils.delayLaunch
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class SubmitRecipeAbstractActivity : BaseActivity() {
    private val mBinding: ActivitySubmitRecipeAbstractBinding by databind(R.layout.activity_submit_recipe_abstract)
    private val mViewModel: RecipeViewModel by viewModels()

    private var registId by Delegates.notNull<Int>()
    private var user by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registId = intent.getIntExtra(KEY_REGIST_ID, 0)
        user = intent.getIntExtra(KEY_USER, 0)
        mBinding.apply {
            lifecycleOwner = this@SubmitRecipeAbstractActivity
            submitAbstract.setOnClickListener {
                mViewModel.submitRecipeInfo(
                    user,
                    registId,
                    recipeDiag.text.toString(),
                    recipeSuggestion.text.toString()
                ).observe(this@SubmitRecipeAbstractActivity) {
                    it.doSuccess {
                        ToastUtils.show(this@SubmitRecipeAbstractActivity, "修改病历成功")
                        lifecycleScope.delayLaunch {
                            startActivity(MainActivity.newClearIntent(this@SubmitRecipeAbstractActivity))
                        }
                    }
                    it.doFailure {
                        ToastUtils.show(this@SubmitRecipeAbstractActivity, "修改病历失败")

                    }
                }
            }
        }
    }

    companion object {
        private const val KEY_REGIST_ID = "key_regist_id"
        private const val KEY_USER = "key_user"
        fun newIntent(context: Context, registId: Int, user: Int): Intent {
            return Intent(context, SubmitRecipeAbstractActivity::class.java).apply {
                putExtra(KEY_REGIST_ID, registId)
                putExtra(KEY_USER, user)
            }
        }
    }
}
