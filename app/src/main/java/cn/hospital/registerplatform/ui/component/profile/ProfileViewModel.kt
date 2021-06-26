package cn.hospital.registerplatform.ui.component.profile

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.hospital.registerplatform.R
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.UserInfo
import cn.hospital.registerplatform.data.repository.UserRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import cn.hospital.registerplatform.ui.component.main.HomeCardData
import cn.hospital.registerplatform.ui.component.recipe.RecipeListActivity
import cn.hospital.registerplatform.ui.component.register.RegisterListActivity
import cn.hospital.registerplatform.utils.ToastUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
    var isDoctor: Boolean = false
    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo
    private val _requireLogin = MutableLiveData(true)
    val requireLogin: LiveData<Boolean> = _requireLogin

    val emptyUserInfo = UserInfo(
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

    fun setEmptyUserInfo() {
        this._userInfo.postValue(emptyUserInfo)
    }

    fun fetchUserInfo() {
        _requireLogin.postValue(true)
        viewModelScope.launch {
            if (!userRepository.requireLogin()) {
                _requireLogin.postValue(false)
                userRepository.getUserInfo().collect {
                    it.doSuccess { info ->
                        _userInfo.postValue(info)
                    }
                }
            }
        }
    }

    fun clearToken() = userRepository.clearToken()

    private fun needLoginToast(
        context: Context,
        ifLoginOperation: () -> Unit
    ) {
        viewModelScope.launch {
            userRepository.needLoginToast(context, ifLoginOperation) {
                setEmptyUserInfo()
            }
        }
    }

    val patientButtonList = listOf(
        HomeCardData(R.string.profile_button_register, R.drawable.ic_baseline_calendar_today_24) {
            it.context.apply {
                needLoginToast(this@apply) {
                    startActivity(RegisterListActivity.newIntent(this@apply))
                }
            }
        },
        HomeCardData(R.string.profile_button_advisory, R.drawable.ic_baseline_message_24) {
            it.context.apply {
                needLoginToast(this@apply) {
                    startActivity(RecipeListActivity.newIntent(this@apply))
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
                needLoginToast(this@apply) {
                    startActivity(RegisterListActivity.newIntent(this@apply))
                }
            }
        },
        HomeCardData(R.string.profile_button_advisory, R.drawable.ic_baseline_message_24) {
            it.context.apply {
                needLoginToast(this@apply) {
                    startActivity(Intent(this, Class.forName("cn.hospital.registerplatform.RecipePatientListActivity")))
                }
            }
        },
        HomeCardData(R.string.profile_button_hospital, R.drawable.ic_baseline_local_hospital_24) {
            ToastUtils.show(it.context, R.string.waiting_for_backend_complete)
        }
    )
}
