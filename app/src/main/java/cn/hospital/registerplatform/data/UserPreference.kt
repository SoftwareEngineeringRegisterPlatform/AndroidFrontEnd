package cn.hospital.registerplatform.data

import android.content.Context
import android.content.SharedPreferences
import cn.hospital.registerplatform.*
import javax.inject.Inject

class UserPreference @Inject constructor(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun cacheToken(token: String): Boolean =
        editor.apply {
            putString(TOKEN_KEY, token)
            apply()
        }.commit()


    fun getCachedToken(): String =
        sharedPreferences.getString(TOKEN_KEY, DEFAULT_TOKEN) ?: DEFAULT_TOKEN


    fun cacheCity(city: String): Boolean =
        editor.apply {
            putString(CURRENT_CITY_KEY, city)
            apply()
        }.commit()

    fun getCity(): String =
        sharedPreferences.getString(CURRENT_CITY_KEY, DEFAULT_CURRENT_CITY) ?: DEFAULT_CURRENT_CITY

}