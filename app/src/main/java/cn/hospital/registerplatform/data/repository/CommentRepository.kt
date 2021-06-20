package cn.hospital.registerplatform.data.repository

import android.util.Log
import androidx.paging.PagingConfig
import cn.hospital.registerplatform.api.interfaces.CommentApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.UploadComment
import cn.hospital.registerplatform.data.dto.suspendFunctionToFlow
import cn.hospital.registerplatform.data.pagingsource.getRawResultList

public enum class COMMENT_SORT_METHOD(val value: String, val id: Int) {
    NEWEST_COMMENT_FIRST("-last_edit_time", 0),
    OLDEST_COMMENT_FIRST("last_edit_time", 1),
    HIGHEST_COMMENT_FIRST("-rating", 2),
    LOWEST_COMMENT_FIRST("rating", 3);
    companion object {
        private val types = values().associate { it.id to it }
        fun fromInt(value: Int) = types[value]
    }
}
public enum class COMMENT_SELECT_METHOD(val value: Int) {
    NO_SELECT_FILETER(0), SELECT_RATING_1(1), SELECT_RATING_2(2),
    SELECT_RATING_3(3), SELECT_RATING_4(4), SELECT_RATING_5(5);
    companion object {
        fun fromInt(value: Int) = values().firstOrNull { it.value == value }
    }
}

class CommentRepository(
    private val commentApi: CommentApi,
    private val userPreference: UserPreference,
    private val pagingConfig: PagingConfig
) {

    fun getCommentList(
        doctorId: Int,
        sort_method: String,
        sort_select: Int
    ) = getRawResultList(pagingConfig) { loadType, page, size ->
        Log.d("sort_select", sort_select.toString())
        if (sort_select.equals(COMMENT_SELECT_METHOD.NO_SELECT_FILETER.value)) { commentApi.getComments(doctorId, loadType, page, size, sort_method) }
        else {
            commentApi.getComments(doctorId, loadType, page, size, sort_method, sort_select)
        }
    }

    fun createComment(
        commentData: UploadComment
    ) = suspendFunctionToFlow<String> {
        commentApi.createComments(commentData)
    }
}
