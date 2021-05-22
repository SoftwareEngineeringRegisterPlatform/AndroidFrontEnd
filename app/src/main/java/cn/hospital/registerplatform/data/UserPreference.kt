package cn.hospital.registerplatform.data

import android.content.Context
import android.content.SharedPreferences
import cn.hospital.registerplatform.DEFAULT_TOKEN
import cn.hospital.registerplatform.SHARED_PREFERENCES_FILE_NAME
import cn.hospital.registerplatform.TOKEN_KEY
import javax.inject.Inject

class UserPreference @Inject constructor(private val context: Context) {

    fun cacheToken(token: String): Boolean {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
        return editor.commit()
    }

    fun getCachedToken(): String {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        return sharedPreferences.getString(TOKEN_KEY, DEFAULT_TOKEN) ?: DEFAULT_TOKEN
    }
}