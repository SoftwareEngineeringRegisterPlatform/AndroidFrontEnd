package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class SingleComment(
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
data class UploadComment(
    @SerializedName("serviceType")
    val serviceType: String,
    @SerializedName("illness")
    val illness: String,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("commentDetail")
    val commentDetail: String,
    @SerializedName("commentTags")
    val commentTags: List<String>,
) : Parcelable

@Parcelize
data class CreateCommentResult(
    @SerializedName("success")
    val success: Boolean
) : Parcelable
