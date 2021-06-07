package cn.hospital.registerplatform.ui.component.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.hospital.registerplatform.api.doFailure
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.UserInfo
import cn.hospital.registerplatform.data.repository.UserRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
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
}
