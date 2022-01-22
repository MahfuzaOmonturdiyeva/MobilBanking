package uz.gita.mobilbanking.domain.usecase.transfer.impl

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import uz.gita.mobilbanking.data.response.HistoryItem
import uz.gita.mobilbanking.domain.repository.TransferRepository
import uz.gita.mobilbanking.domain.usecase.transfer.TransfersHistoryUseCase
import javax.inject.Inject

class TransfersHistoryUseCaseImpl @Inject constructor(
    private val transferRepository: TransferRepository
): TransfersHistoryUseCase {
    override suspend fun getHistory(scope: CoroutineScope): LiveData<PagingData<HistoryItem>> = transferRepository.getHistory(scope)

    override suspend fun outcomes(scope: CoroutineScope): LiveData<PagingData<HistoryItem>> =transferRepository.outcomes(scope)

    override suspend fun incomes(scope: CoroutineScope): LiveData<PagingData<HistoryItem>> =transferRepository.incomes(scope)
}