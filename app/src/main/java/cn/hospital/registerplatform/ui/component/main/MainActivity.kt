package cn.hospital.registerplatform.ui.component.main

import android.os.Bundle
import androidx.activity.viewModels
import cn.hospital.registerplatform.databinding.ActivityMainBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import cn.hospital.registerplatform.ui.component.comment.CommentListActivity
import cn.hospital.registerplatform.ui.component.comment.SubmitCommentActivity
import cn.hospital.registerplatform.ui.component.hospital.HospitalDetailActivity
import com.hi.dhl.jdatabinding.binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mViewModel: MainViewModel by viewModels()
    private val mBinding: ActivityMainBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            toDetailButton.setOnClickListener {
                startActivity(HospitalDetailActivity.newIntent(this@MainActivity, 0))
            }
            toListButton.setOnClickListener {
                startActivity(CommentListActivity.newIntent(this@MainActivity, 0))
            }
            toSubmitButton.setOnClickListener {
                startActivity(SubmitCommentActivity.newIntent(this@MainActivity, 0))
            }
        }
    }

}