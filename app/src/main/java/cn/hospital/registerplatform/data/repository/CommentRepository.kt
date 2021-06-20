package cn.hospital.registerplatform.data.repository

import android.util.Log
import androidx.paging.PagingConfig
import cn.hospital.registerplatform.api.interfaces.CommentApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.UploadComment
import cn.hospital.registerplatform.data.dto.suspendFunctionToFlow
import cn.hospital.registerplatform.data.pagingsource.getRawResultList

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
        if (sort_select.equals(0)) { commentApi.getComments(doctorId, loadType, page, size, sort_method) }
        else {
            Log.d("sort_select", sort_select.toString())
            commentApi.getComments(doctorId, loadType, page, size, sort_method, sort_select)
        }
    }

    fun createComment(
        commentData: UploadComment
    ) = suspendFunctionToFlow<String> {
        commentApi.createComments(commentData)
    }
}
