package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResult(
    @SerializedName("succeed")
    val succeed: Boolean,
    @SerializedName("permission")
    val permission: UserPermission?,
    @SerializedName("token")
    val token: String?
) : Parcelable

@Parcelize
data class UserPermission(
    @SerializedName("collector")
    val collector: Boolean,
    @SerializedName("analyzer")
    val analyzer: Boolean
) : Parcelable