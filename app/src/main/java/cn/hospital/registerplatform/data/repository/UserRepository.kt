package cn.hospital.registerplatform.data.repository

import cn.hospital.registerplatform.api.interfaces.UserApi
import cn.hospital.registerplatform.data.UserPreference

class UserRepository(
    private val userApi: UserApi,
    private val userPreference: UserPreference
) {
}
