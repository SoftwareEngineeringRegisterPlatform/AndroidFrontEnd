package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class RecipeListItem(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("user")
    val user: String,
    @SerializedName("date")
    val date: Date,
    @SerializedName("regist")
    val regist: Int,
    @SerializedName("diag")
    val diag: String,
    @SerializedName("suggestion")
    val suggestion: String,
) : Parcelable, ListItem


@Parcelize
data class RecipeCombinedListItem(
    override val id: Int,
    val recipeListItem: RecipeListItem,
    var recipeInfo: RecipeInfo,
    val doctorInfo: DoctorInfo
) : Parcelable, ListItem

@Parcelize
data class RecipeInfo(
    @SerializedName("user")
    val user: String,
    @SerializedName("date")
    val date: Date,
    @SerializedName("regist")
    val regist: Int,
    @SerializedName("diag")
    val diag: String,
    @SerializedName("suggestion")
    val suggestion: String,
    @SerializedName("exam_result")
    val exam_result: List<Int>,
    @SerializedName("prescription")
    val prescription: List<Int>
) : Parcelable

@Parcelize
data class ExamInfo(
    @SerializedName("date")
    val date: Date,
    @SerializedName("user")
    val user: Int,
    @SerializedName("diag")
    val diag: String,
    @SerializedName("content")
    val content: String,
) : Parcelable

@Parcelize
data class PrescriptionInfo(
    @SerializedName("user")
    val user: Int,
    @SerializedName("date")
    val date: Date,
    @SerializedName("medicine")
    val medicine: String,
) : Parcelable

@Parcelize
data class RecipeDetailCombinedListItem(
    override val id: Int,
    val date: Date,
    val isExam: Boolean,
    var examInfo: ExamInfo?,
    var prescriptionInfo: PrescriptionInfo?
) : Parcelable, ListItem