package cn.hospital.registerplatform.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cn.hospital.registerplatform.api.Resource
import cn.hospital.registerplatform.api.interfaces.CommentApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.CreateCommentResult
import cn.hospital.registerplatform.data.dto.SingleComment
import cn.hospital.registerplatform.data.dto.UploadComment
import cn.hospital.registerplatform.data.pagingsource.CommentPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CommentRepositoryImpl(
    private val commentApi: CommentApi,
    private val userPreference: UserPreference
) : CommentRepository {
    private val pagingConfig = PagingConfig(
        pageSize = 20,

        enablePlaceholders = true,

        prefetchDistance = 4,

        initialLoadSize = 20
    )

    override fun getComment(
        hospitalId: Int
    ): Flow<PagingData<SingleComment>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                CommentPagingSource(
                    commentApi,
                    userPreference,
                    hospitalId
                )
            }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun createComment(
        hospitalId: Int,
        uploadComment: UploadComment
    ): Flow<Resource<CreateCommentResult>> =
        flow {
            try {
                val token = userPreference.getCachedToken()
                val res = commentApi.createComments(token, hospitalId, uploadComment)
                emit(Resource.Success(res))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
}