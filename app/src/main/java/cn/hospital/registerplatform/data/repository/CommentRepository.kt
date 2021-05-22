package cn.hospital.registerplatform.data.repository

import androidx.paging.PagingData
import cn.hospital.registerplatform.api.Resource
import cn.hospital.registerplatform.data.dto.CreateCommentResult
import cn.hospital.registerplatform.data.dto.SingleComment
import cn.hospital.registerplatform.data.dto.UploadComment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getComment(hospitalId: Int): Flow<PagingData<SingleComment>>
    fun createComment(
        hospitalId: Int,
        uploadComment: UploadComment
    ): Flow<Resource<CreateCommentResult>>
}