package cn.hospital.registerplatform.data.pagingsource

import androidx.paging.*
import cn.hospital.registerplatform.data.dto.LoadType
import cn.hospital.registerplatform.data.dto.RawResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class HospitalPagingSource<T : Any>(
    private val loadResult: suspend (page: Int, size: Int) -> List<T>
) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val position = params.key ?: 0
        val res = loadResult(position, params.loadSize)
        return LoadResult.Page(
            data = res,
            prevKey = if (position == 0) null else position - 1,
            nextKey = if (res.isEmpty()) null else position + 1
        )
    }
}

fun <T : Any> getRawResultList(
    pagingConfig: PagingConfig,
    getListFromApi: suspend (LoadType, page: Int, size: Int) -> RawResult<List<T>>
): Flow<PagingData<T>> {
    return Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            HospitalPagingSource { page: Int, size: Int ->
                try {
                    val rawResult = getListFromApi(LoadType.PAGE, page, size)
                    if (rawResult.success) rawResult.content else listOf()
                } catch (e: Exception) {
                    e.printStackTrace()
                    listOf()
                }
            }
        }
    ).flow.flowOn(Dispatchers.IO)
}

fun <T : Any> getList(
    pagingConfig: PagingConfig,
    getListFromApi: suspend (LoadType, page: Int, size: Int) -> List<T>
): Flow<PagingData<T>> {
    return Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            HospitalPagingSource { page: Int, size: Int ->
                try {
                    getListFromApi(LoadType.PAGE, page, size)
                } catch (e: Exception) {
                    e.printStackTrace()
                    listOf()
                }
            }
        }
    ).flow.flowOn(Dispatchers.IO)
}
