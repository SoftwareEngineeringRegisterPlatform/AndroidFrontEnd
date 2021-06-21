package cn.hospital.registerplatform.data.dto

import android.content.Context
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import cn.hospital.registerplatform.R
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class HospitalFilter(
    @SerializedName("name")
    var name: String,
    @SerializedName("type")
    var type: HospitalSearchCondition
) : Parcelable {
    companion object {
        fun fromData(searchInput: String, searchConditionIndex: Int = 0): HospitalFilter {
            return HospitalFilter(
                name = searchInput,
                type = HospitalSearchCondition.fromIndex(searchConditionIndex)
            )
        }
    }

    fun toJsonString(context: Context): String {
        val searchConditionArray = context.resources.getStringArray(R.array.hospital_search_condition)
        return "{${"name:$name"}${if (type == HospitalSearchCondition.ALL) "" else ",type:${searchConditionArray[type.index]}"}}"
    }
}

enum class HospitalSearchCondition(val index: Int) {
    ALL(0),
    SPECIALIZED(1),
    GENERAL(2),
    CHINESE_MEDICINE(3),
    INTEGRATED_CHINESE_AND_WESTERN_MEDICINE(4),
    MATERNITY_AND_CHILD(5);

    companion object {
        fun fromIndex(index: Int): HospitalSearchCondition {
            return values().firstOrNull { it.index == index } ?: ALL
        }
    }
}

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
    @SerializedName("level")
    val level: String,
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
    @SerializedName("level")
    val level: String,
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
    @SerializedName("qualification")
    val qualification: String,
    @SerializedName("field")
    val field: String,
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
    @SerializedName("qualification")
    val qualification: String,
) : Parcelable, ListItem

@Parcelize
data class DoctorInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("position")
    val position: String?,
    @SerializedName("qualification")
    val qualification: String?,
    @SerializedName("hospital__name")
    val hospitalName: String,
    @SerializedName("department__name")
    val departmentName: String,
    @SerializedName("hospital__id")
    val hospitalId: Int,
    @SerializedName("department__id")
    val departmentId: Int,
    @SerializedName("rating")
    val averageRating: Float,
    @SerializedName("n_rating")
    val commentsNum: Int,
) : Parcelable {
    companion object {
        fun emptyDoctorInfo(): DoctorInfo {
            return DoctorInfo(
                0,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                0,
                0,
                0.0f,
                0
            )
        }
    }
}

@Parcelize
data class ScheduleInfo(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("doctor__id")
    val doctorId: Int,
    @SerializedName("doctor__name")
    val doctorName: String,
    @SerializedName("begin_time")
    val begin_time: Date,
    @SerializedName("end_time")
    val end_time: Date,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("registed")
    val registed: Int,
) : Parcelable, ListItem {
    companion object {
        fun emptyScheduleInfo(): ScheduleInfo {
            return ScheduleInfo(
                0,
                0,
                "",
                Date(),
                Date(),
                0,
                0
            )
        }
    }
}

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
