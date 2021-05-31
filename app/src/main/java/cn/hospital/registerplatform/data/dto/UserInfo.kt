package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewUserInfo(
    @SerializedName("password")
    val password: String,
    @SerializedName("userinfo")
    val userinfo: UploadUserInfo,
) : Parcelable

@Parcelize
data class UploadUserInfo(
    @SerializedName("user_name")
    val user_name: String,
    @SerializedName("signature")
    val signature: String,
    @SerializedName("phone_number")
    val phone_number: String,
    @SerializedName("portrait")
    val portrait: String
) : Parcelable

@Parcelize
data class UserInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_name")
    val user_name: String,
    @SerializedName("signature")
    val signature: String,
    @SerializedName("portrait")
    val portrait: String,
) : Parcelable
