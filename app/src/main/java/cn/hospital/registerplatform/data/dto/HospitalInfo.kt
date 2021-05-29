package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
    @SerializedName("hospital")
    val hospital: Int,
    @SerializedName("department")
    val department: Int,
    @SerializedName("picture")
    val picture: String,
) : Parcelable, ListItem

@Parcelize
data class DoctorInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("hospital")
    val hospital: Int,
    @SerializedName("department")
    val department: Int,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("qualification")
    val qualification: String,
) : Parcelable

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
