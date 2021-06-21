package cn.hospital.registerplatform.data.repository

import android.util.Log
import androidx.paging.PagingConfig
import cn.hospital.registerplatform.api.interfaces.CommentApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.UploadComment
import cn.hospital.registerplatform.data.dto.suspendFunctionToFlow
import cn.hospital.registerplatform.data.pagingsource.getRawResultList

enum class CommentSortMethod(val value: String, val id: Int) {
    NEWEST_COMMENT_FIRST("-last_edit_time", 0),
    OLDEST_COMMENT_FIRST("last_edit_time", 1),
    HIGHEST_COMMENT_FIRST("-rating", 2),
    LOWEST_COMMENT_FIRST("rating", 3);

    override fun toString(): String {
        return value
    }

    companion object {
        private val types = values().associateBy { it.id }
        fun fromInt(value: Int) = types[value] ?: NEWEST_COMMENT_FIRST
    }
}

enum class CommentRatingFilter(val value: Int) {
    NO_SELECT_FILTER(0),
    SELECT_RATING_1(1),
    SELECT_RATING_2(2),
    SELECT_RATING_3(3),
    SELECT_RATING_4(4),
    SELECT_RATING_5(5);

    override fun toString(): String {
        return value.toString()
    }

    companion object {
        fun fromInt(value: Int) = values().firstOrNull { it.value == value } ?: NO_SELECT_FILTER
    }
}

class CommentRepository(
    private val commentApi: CommentApi,
    private val userPreference: UserPreference,
    private val pagingConfig: PagingConfig
) {

    fun getCommentList(
        doctorId: Int,
        sortMethod: CommentSortMethod,
        ratingFilter: CommentRatingFilter
    ) = getRawResultList(pagingConfig) { loadType, page, size ->
        Log.d("sort_select", ratingFilter.toString())
        if (ratingFilter == CommentRatingFilter.NO_SELECT_FILTER) {
            commentApi.getComments(doctorId, loadType, page, size, sortMethod)
        } else {
            commentApi.getComments(doctorId, loadType, page, size, sortMethod, ratingFilter)
        }
    }

    fun createComment(
        commentData: UploadComment
    ) = suspendFunctionToFlow<String> {
        commentApi.createComments(commentData)
    }
}
