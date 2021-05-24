package cn.hospital.registerplatform.ui.component.main

import androidx.lifecycle.liveData
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userPreference: UserPreference) :
    BaseViewModel() {
    val buttonList = listOf(
        HomeCardData(R.string.title_home, R.drawable.ic_baseline_add_box_24) {},
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