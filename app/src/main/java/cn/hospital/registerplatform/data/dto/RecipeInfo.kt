package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeListItem(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("time")
    val time: String,
    @SerializedName("recipe")
    val recipe: Int,
    @SerializedName("diag")
    val diag: String,
    @SerializedName("suggestion")
    val suggestion: String,
) : Parcelable, ListItem

@Parcelize
data class RecipeInfo(
    @SerializedName("time")
    val time: String,
    @SerializedName("receipe")
    val recipe: Int,
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
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("receipe")
    val receipe: Int,
    @SerializedName("diag")
    val diag: String,
    @SerializedName("content")
    val content: String,
) : Parcelable

@Parcelize
data class PrescriptionInfo(
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("receipe")
    val receipe: Int,
    @SerializedName("medicine")
    val medicine: String,
) : Parcelable
