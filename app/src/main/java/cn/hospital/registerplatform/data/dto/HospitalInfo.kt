package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class HospitalListItem(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("type")
    val type: String,
) : Parcelable, ListItem

@Parcelize
data class HospitalInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("locate_detail")
    val locate_detail: String,
    @SerializedName("locate_lat")
    val locate_lat: Double,
    @SerializedName("locate_lng")
    val locate_lng: Double,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("type")
    val type: String,
) : Parcelable

@Parcelize
data class DepartmentListItem(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("fatherDepartment")
    val fatherDepartment: Int?,
    @SerializedName("picture")
    val picture: String
) : Parcelable, ListItem


@Parcelize
data class DepartmentInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("hospital")
    val hospital: Int,
    @SerializedName("fatherDepartment")
    val fatherDepartment: Int?,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("picture")
    val picture: String,
) : Parcelable

@Parcelize
data class DoctorListItem(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("hospital__name")
    val hospital_name: String,
    @SerializedName("department__name")
    val department_name: String,
    @SerializedName("picture")
    val picture: String,
) : Parcelable, ListItem

@Parcelize
data class DoctorInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("hospital_name")
    val hospital_name: String,
    @SerializedName("department_name")
    val department_name: String,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("qualification")
    val qualification: String,
) : Parcelable

@Parcelize
data class ScheduleInfo(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("doctor__name")
    val doctor__name: String,
    @SerializedName("begin_time")
    val begin_time: Date,
    @SerializedName("end_time")
    val end_time: Date,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("registed")
    val registed: Int,
) : Parcelable, ListItem

interface ListItem {
    val id: Int

    companion object : ListItemCompanion {
        override fun <T : ListItem> getDiffCallback(): DiffUtil.ItemCallback<T> {
            return object : DiffUtil.ItemCallback<T>() {
                override fun areItemsTheSame(
                    oldItem: T,
                    newItem: T
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: T,
                    newItem: T
                ) = oldItem.id == newItem.id
            }
        }
    }
}

interface ListItemCompanion {
    fun <T : ListItem> getDiffCallback(): DiffUtil.ItemCallback<T>
}
