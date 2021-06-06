package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CommentListItem(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("last_edit_time")
    val last_edit_time: Date,
) : Parcelable, ListItem

@Parcelize
data class CreateCommentResult(
    @SerializedName("success")
    val success: Boolean
) : Parcelable

@Parcelize
data class UploadComment(
    @SerializedName("recipe_id")
    val recipe_id: Int,
    @SerializedName("token")
    val token: String,
    @SerializedName("evaluateinfo")
    val evaluateinfo: EvaluateInfo,
) : Parcelable

@Parcelize
data class EvaluateInfo(
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("comment")
    val comment: String,
) : Parcelable
