package cn.hospital.registerplatform.ui.component.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doFailure
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.UserInfo
import cn.hospital.registerplatform.data.repository.UserRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import cn.hospital.registerplatform.ui.component.main.HomeCardData
import cn.hospital.registerplatform.ui.component.recipe.RecipeListActivity
import cn.hospital.registerplatform.ui.component.recipe.RecipeListEditActivity
import cn.hospital.registerplatform.ui.component.register.RegisterListActivity
import cn.hospital.registerplatform.utils.ToastUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo
    private val _requireLogin = MutableLiveData(true).apply {
        postValue(userRepository.requireLogin())
    }
    val requireLogin: LiveData<Boolean> = _requireLogin

    fun fetchUserInfo() {
        if (!userRepository.requireLogin()) {
            _requireLogin.postValue(false)
            viewModelScope.launch {
                userRepository.getUserInfo().collect {
                    it.doSuccess { info ->
                        _userInfo.postValue(info)
                    }
                    it.doFailure {
                        userRepository.clearToken()
                        _requireLogin.postValue(true)
                        _userInfo.postValue(
                            UserInfo(
                                "点击登陆用户",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                null,
                                null,
                                "",
                                "",
                                ""
                            )
                        )
                    }
                }
            }
        } else {
            _requireLogin.postValue(true)
            _userInfo.postValue(
                UserInfo(
                    "点击登陆用户",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    null,
                    null,
                    "",
                    "",
                    ""
                )
            )
        }
    }

    fun clearToken() = userRepository.clearToken()

    val patientButtonList = listOf(
        HomeCardData(R.string.profile_button_register, R.drawable.ic_baseline_calendar_today_24) {
            it.context.apply {
                userRepository.needLoginToast(this) {
                    startActivity(RegisterListActivity.newIntent(this))
                }
            }
        },
        HomeCardData(R.string.profile_button_advisory, R.drawable.ic_baseline_message_24) {
            it.context.apply {
                userRepository.needLoginToast(this) {
                    startActivity(RecipeListActivity.newIntent(this))
                }
            }
        },
        HomeCardData(R.string.profile_button_doctor, R.drawable.ic_baseline_medical_services_24) {
            ToastUtils.show(it.context, R.string.waiting_for_backend_complete)
        },
        HomeCardData(R.string.profile_button_hospital, R.drawable.ic_baseline_local_hospital_24) {
            ToastUtils.show(it.context, R.string.waiting_for_backend_complete)
        }
    )

    val doctorButtonList = listOf(
        HomeCardData(R.string.profile_button_register, R.drawable.ic_baseline_calendar_today_24) {
            it.context.apply {
                userRepository.needLoginToast(this) {
                    startActivity(RegisterListActivity.newIntent(this))
                }
            }
        },
        HomeCardData(R.string.profile_button_advisory, R.drawable.ic_baseline_message_24) {
            it.context.apply {
                userRepository.needLoginToast(this) {
                    startActivity(RecipeListEditActivity.newIntent(this))
                }
            }
        },
        HomeCardData(R.string.profile_button_hospital, R.drawable.ic_baseline_local_hospital_24) {
            ToastUtils.show(it.context, R.string.waiting_for_backend_complete)
        }
    )
}
