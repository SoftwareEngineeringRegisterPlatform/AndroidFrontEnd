package cn.hospital.registerplatform.ui.component.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.databinding.ActivityMainBinding
import cn.hospital.registerplatform.ui.base.BaseActivity
import com.hi.dhl.binding.databind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by databind(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.navView.apply {
            setupWithNavController(findNavController(R.id.nav_host_fragment_activity_main))
            menu.getItem(1).isEnabled = false
        }
    }
}