package cn.hospital.registerplatform.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cn.hospital.registerplatform.api.interfaces.CommentApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.SingleComment
import okio.IOException
import retrofit2.HttpException

class CommentPagingSource(
    private val commentApi: CommentApi,
    private val userPreference: UserPreference,
    private val hospitalId: Int
) : PagingSource<Int, SingleComment>() {
    override fun getRefreshKey(state: PagingState<Int, SingleComment>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SingleComment> {
//        val token = userPreference.getCachedToken()
        val position = params.key ?: 1
        return try {
            val matchList =
                commentApi.getComments(hospitalId, position, params.loadSize).commentList
            LoadResult.Page(
                data = matchList,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (matchList.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}