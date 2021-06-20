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
data class RecipeDoctorCombinedListItem(
    override val id: Int,
    var recipeInfo: RecipeInfo,
    val userInfo: UserInfo,
    val hasRecipe: Boolean
) : Parcelable, ListItem


@Parcelize
data class RegistWithRecipeListItem(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("user")
    val user: Int,
    @SerializedName("date")
    val date: Date,
    @SerializedName("schedule__doctor__id")
    val doctorId: Int,
    @SerializedName("schedule")
    val schedule: Int,
    @SerializedName("status")
    val status: RegisterStatus,
) : Parcelable, ListItem

@Parcelize
data class RecipeInfo(
    @SerializedName("user")
    val user: Int,
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
data class RecipeInfoSubmitBody(
    @SerializedName("token")
    val token: String,
    @SerializedName("recipe_info")
    val recipeInfo: RecipeInfoSubmit,
) : Parcelable

@Parcelize
data class RecipeInfoSubmit(
    @SerializedName("user")
    val user: Int,
    @SerializedName("regist")
    val regist: Int,
    @SerializedName("diag")
    val diag: String,
    @SerializedName("suggestion")
    val suggestion: String
) : Parcelable


@Parcelize
data class RecipeInfoEditBody(
    @SerializedName("token")
    val token: String,
    @SerializedName("recipe_id")
    val recipeId: Int,
    @SerializedName("recipe_info")
    val recipeInfo: RecipeInfoEdit
) : Parcelable

@Parcelize
data class RecipeInfoEdit(
    @SerializedName("diag")
    val diag: String,
    @SerializedName("suggestion")
    val suggestion: String
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
data class ExamInfoSubmitBody(
    @SerializedName("token")
    val token: String,
    @SerializedName("exam_info")
    val examInfo: ExamInfoSubmit,
) : Parcelable

@Parcelize
data class ExamInfoSubmit(
    @SerializedName("user")
    val user: Int,
    @SerializedName("regist")
    val regist: Int,
    @SerializedName("diag")
    val diag: String,
    @SerializedName("content")
    val content: String,
) : Parcelable

@Parcelize
data class ExamInfoEditBody(
    @SerializedName("token")
    val token: String,
    @SerializedName("exam_id")
    val examId: Int,
    @SerializedName("exam_info")
    val examInfo: ExamInfoEdit
) : Parcelable

@Parcelize
data class ExamInfoEdit(
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
data class PrescriptionInfoSubmitBody(
    @SerializedName("token")
    val token: String,
    @SerializedName("prescirption_info")
    val prescirptionInfo: PrescriptionInfoSubmit,
) : Parcelable

@Parcelize
data class PrescriptionInfoSubmit(
    @SerializedName("user")
    val user: Int,
    @SerializedName("medicine")
    val medicine: String,
) : Parcelable


@Parcelize
data class PrescriptionInfoEditBody(
    @SerializedName("token")
    val token: String,
    @SerializedName("prescription_id")
    val prescriptionId: Int,
    @SerializedName("prescription_info")
    val prescriptionInfo: PrescriptionInfoEdit
) : Parcelable

@Parcelize
data class PrescriptionInfoEdit(
    @SerializedName("medicine")
    val medicine: String,
) : Parcelable

@Parcelize
data class RecipeDetailCombinedListItem(
    override val id: Int,
    val examId: Int,
    val date: Date,
    val isExam: Boolean,
    var examInfo: ExamInfo?,
    var prescriptionInfo: PrescriptionInfo?
) : Parcelable, ListItem