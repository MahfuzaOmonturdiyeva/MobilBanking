package uz.gita.mobilbanking.domain.usecase.transfer

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import uz.gita.mobilbanking.data.response.HistoryItem

interface TransfersHistoryUseCase {
    suspend fun getHistory(scope: CoroutineScope): LiveData<PagingData<HistoryItem>>

    suspend fun outcomes(scope: CoroutineScope): LiveData<PagingData<HistoryItem>>

    suspend fun incomes(scope: CoroutineScope): LiveData<PagingData<HistoryItem>>
}