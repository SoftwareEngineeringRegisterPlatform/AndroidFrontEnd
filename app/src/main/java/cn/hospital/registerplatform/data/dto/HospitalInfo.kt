package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HospitalListItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String,
) : Parcelable

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
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("fatherDepartment")
    val fatherDepartment: Int?,
    @SerializedName("picture")
    val picture: String
) : Parcelable


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
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("hospital")
    val hospital: Int,
    @SerializedName("department")
    val department: Int,
    @SerializedName("picture")
    val picture: String,
) : Parcelable

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