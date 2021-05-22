package cn.hospital.registerplatform.data.dto

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawCommentList(
    @SerializedName("comment_list")
    val commentList: List<SingleComment>
) : Parcelable

@Parcelize
data class SingleComment(
    @SerializedName("commentId")
    val commentId: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("serviceType")
    val serviceType: String,
    @SerializedName("illness")
    val illness: String,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("commentTime")
    val commentTime: String,
    @SerializedName("commentDetail")
    val commentDetail: String,
    @SerializedName("commentTags")
    val commentTags: List<String>,
) : Parcelable {
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<SingleComment> =
            object : DiffUtil.ItemCallback<SingleComment>() {
                override fun areItemsTheSame(
                    oldItem: SingleComment,
                    newItem: SingleComment
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: SingleComment,
                    newItem: SingleComment
                ) = oldItem.commentId == newItem.commentId
            }
    }
}

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