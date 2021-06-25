package cn.hospital.registerplatform.ui.component.main

import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.repository.UserRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import cn.hospital.registerplatform.ui.component.hospital.HospitalListActivity
import cn.hospital.registerplatform.ui.component.recipe.RecipeListActivity
import cn.hospital.registerplatform.ui.component.register.RegisterListActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreference: UserPreference
) :
    BaseViewModel() {
    val buttonList = listOf(
        HomeCardData(R.string.home_register_button, R.drawable.ic_baseline_calendar_today_24) {
            it.context.apply {
                startActivity(HospitalListActivity.newIntent(this))
            }
        },
        HomeCardData(R.string.home_hospital_button, R.drawable.ic_baseline_add_box_24) {
            it.context.apply {
                startActivity(HospitalListActivity.newIntent(this))
            }
        },
        HomeCardData(R.string.home_register_history_button, R.drawable.ic_baseline_access_time_24) {
            it.context.apply {
                viewModelScope.launch {
                    userRepository.needLoginToast(this@apply) {
                        startActivity(RegisterListActivity.newIntent(this@apply))
                    }
                }
            }
        },
        HomeCardData(R.string.home_recipe_button, R.drawable.ic_baseline_message_24) {
            it.context.apply {
                viewModelScope.launch {
                    userRepository.needLoginToast(this@apply) {
                        startActivity(RecipeListActivity.newIntent(this@apply))
                    }
                }
            }
        }
    )

    val city = liveData {
        emit(userPreference.getCity())
    }

}
