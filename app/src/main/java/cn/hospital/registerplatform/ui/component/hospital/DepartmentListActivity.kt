package cn.hospital.registerplatform.ui.component.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.hospital.registerplatform.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepartmentListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_list)
    }
}
