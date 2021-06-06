package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterListItem(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("schedule")
    val schedule: Int,
    @SerializedName("status")
    val status: RegisterStatus,
) : Parcelable, ListItem


@Parcelize
data class RegisterInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user")
    val user: Int,
    @SerializedName("schedule")
    val schedule: Int,
    @SerializedName("status")
    val status: RegisterStatus,
) : Parcelable

enum class RegisterStatus {
    @SerializedName("Waiting")
    WAITING,

    @SerializedName("Finished")
    FINISHED
}
