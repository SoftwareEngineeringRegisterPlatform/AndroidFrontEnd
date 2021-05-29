package cn.hospital.registerplatform.ui.component.main

import androidx.lifecycle.liveData
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.ui.base.BaseViewModel
import cn.hospital.registerplatform.ui.component.hospital.HospitalListActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userPreference: UserPreference) :
    BaseViewModel() {
    val buttonList = listOf(
        HomeCardData(R.string.home_hospital_button, R.drawable.ic_baseline_add_box_24) {
            it.context.apply {
                startActivity(HospitalListActivity.newIntent(this))
            }
        },
        HomeCardData(R.string.title_home, R.drawable.ic_baseline_add_box_24) {},
        HomeCardData(R.string.title_home, R.drawable.ic_baseline_add_box_24) {},
        HomeCardData(R.string.title_home, R.drawable.ic_baseline_add_box_24) {},
        HomeCardData(R.string.title_home, R.drawable.ic_baseline_add_box_24) {},
        HomeCardData(R.string.title_home, R.drawable.ic_baseline_add_box_24) {},
        HomeCardData(R.string.title_home, R.drawable.ic_baseline_add_box_24) {},
    )

    val city = liveData {
        emit(userPreference.getCity())
    }

}
