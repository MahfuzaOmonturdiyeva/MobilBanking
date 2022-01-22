package uz.gita.mobilbanking.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.*

interface TransferRepository {

    fun sendMoney(data: SendMoneyRequest): LiveData<MyResult<SendMoneyResponse>>

    fun fee(feeRequest: FeeRequest): LiveData<MyResult<Double>>

    suspend fun getHistory(scope: CoroutineScope): LiveData<PagingData<HistoryItem>>

    suspend fun outcomes(scope: CoroutineScope): LiveData<PagingData<HistoryItem>>

    suspend fun incomes(scope: CoroutineScope): LiveData<PagingData<HistoryItem>>
}