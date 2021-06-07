package cn.hospital.registerplatform.data.repository

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
        doctorId: Int
    ) = getRawResultList(pagingConfig) { loadType, page, size ->
        commentApi.getComments(doctorId, loadType, page, size)
    }

    fun createComment(
        commentData: UploadComment
    ) = suspendFunctionToFlow<String> {
        commentApi.createComments(commentData)
    }
}
