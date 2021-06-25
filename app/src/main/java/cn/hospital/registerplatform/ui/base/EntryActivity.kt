package cn.hospital.registerplatform.ui.base

import cn.hospital.registerplatform.ui.component.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryActivity : BaseActivity() {
    override fun onResume() {
        super.onResume()
        startActivity(MainActivity.newClearIntent(this))
    }

    override fun onStart() {
        super.onStart()
        setVisible(true)
    }
}
