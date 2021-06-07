package cn.hospital.registerplatform.ui.base

import android.os.Bundle

abstract class ActionBarActivity(private val actionBarTitle: String) : BaseActivity() {
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
