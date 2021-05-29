package cn.hospital.registerplatform.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState

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
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (res.isEmpty()) null else position + 1
        )
    }
}
