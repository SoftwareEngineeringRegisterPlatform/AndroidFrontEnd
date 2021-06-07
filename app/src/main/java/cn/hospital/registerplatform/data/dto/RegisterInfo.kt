package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class RegisterListItem(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("date")
    val date: Date,
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

enum class RegisterStatus(private val stringValue: String) {
    @SerializedName("Waiting")
    WAITING("等待看病"),

    @SerializedName("Finished")
    FINISHED("已完成");

    override fun toString(): String {
        return stringValue
    }
}
