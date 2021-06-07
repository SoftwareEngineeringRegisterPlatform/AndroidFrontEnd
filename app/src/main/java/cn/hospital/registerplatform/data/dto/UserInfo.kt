package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewUserData(
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("phone_number")
    val phone_number: String,
    @SerializedName("password")
    val password: String,
) : Parcelable

@Parcelize
data class WrapUploadUserInfo(
    @SerializedName("token")
    val token: String,
    @SerializedName("userinfo")
    val userInfo: UploadUserInfo
) : Parcelable

@Parcelize
data class UploadUserInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("height")
    val height: Double,
    @SerializedName("weight")
    val weight: Double,
    @SerializedName("blood_type")
    val bloodType: String,
    @SerializedName("diseasy_history")
    val diseaseHistory: String,
) : Parcelable

@Parcelize
data class UserInfo(
    @SerializedName("name")
    val name: String?,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("signature")
    val signature: String?,
    @SerializedName("portrait")
    val portrait: String,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("weight")
    val weight: Double?,
    @SerializedName("blood_type")
    val bloodType: String?,
    @SerializedName("job")
    val job: String?,
    @SerializedName("diseasy_history")
    val diseaseHistory: String?,
) : Parcelable
