package cn.hospital.registerplatform.ui.component.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.ActivityMainBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import cn.hospital.registerplatform.ui.component.hospital.HospitalListActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by databind(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            fab.apply {
                setOnClickListener {
                    if (this.isExtended) {
                        startActivity(HospitalListActivity.newIntent(this@MainActivity))
                    } else {
                        extend()
                    }
                }
            }
            navView.apply {
                setupWithNavController((supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController)
                menu.getItem(1).isEnabled = false
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
