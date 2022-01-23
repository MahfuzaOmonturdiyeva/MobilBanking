package uz.gita.mobilbanking.data.source.remote.api.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import timber.log.Timber
import uz.gita.mobilbanking.data.response.HistoryItem
import uz.gita.mobilbanking.data.source.remote.api.api.CardApi
import uz.gita.mobilbanking.data.source.remote.api.api.TransferApi

class OutcomesDataSource(val transferApi: TransferApi, val cardApi: CardApi) :
    PagingSource<Int, HistoryItem>() {

    override fun getRefreshKey(state: PagingState<Int, HistoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryItem> {
        return try {
            val nextPageNumber = params.key ?: 0
            val pageSize = 10
            val response = transferApi.outcomes(nextPageNumber, pageSize)

            response.body()?.data?.data?.let {
                for (i in it) {
                    if (i.receiver != null) {
                        val responseOwner = cardApi.ownerById(i.receiver)
                        responseOwner.body()?.data?.fio?.let { it1 ->
                            i.owner = it1
                        }
                    }
                }
            }

            Timber.d(response.isSuccessful.toString())
            Timber.d(response.body().toString())

            LoadResult.Page(
                data = response.body()!!.data!!.data,
//                prevKey = null,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < (response.body()!!.data!!.totalCount / response.body()!!.data!!.pageSize) + 1)
                    nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            Timber.d("e = $e")
            LoadResult.Error(Throwable("Ulanishda xatolik bo'ldi"))
        }
    }

}