package uz.gita.mobilbanking.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.FeeRequest
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.data.response.HistoryItem
import uz.gita.mobilbanking.data.response.ResponseData
import uz.gita.mobilbanking.data.response.SendMoneyResponse
import uz.gita.mobilbanking.data.source.local.LocalStorage
import uz.gita.mobilbanking.data.source.remote.api.api.CardApi
import uz.gita.mobilbanking.data.source.remote.api.dataSource.HistoryDataSource
import uz.gita.mobilbanking.data.source.remote.api.api.TransferApi
import uz.gita.mobilbanking.data.source.remote.api.dataSource.IncomesDataSource
import uz.gita.mobilbanking.data.source.remote.api.dataSource.OutcomesDataSource
import uz.gita.mobilbanking.domain.repository.TransferRepository
import java.io.IOException
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val transferApi: TransferApi,
    private val cardApi: CardApi,
    private val localStorage: LocalStorage
) : TransferRepository {

    override fun sendMoney(data: SendMoneyRequest): LiveData<MyResult<SendMoneyResponse>> =
        liveData {
            try {
                val response = transferApi.sendMoney(data)
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        emit(MyResult.Success<SendMoneyResponse>(it))
                        return@liveData
                    }
                    emit(MyResult.Message<SendMoneyResponse>("server bilan bog'lanishda xatolik"))
                    return@liveData
                } else {
                    if (response.code() == 401)
                        emit(MyResult.Logout<SendMoneyResponse>())
                    else {
                        response.errorBody()?.let {
                            val message = Gson().fromJson(it.string(), ResponseData::class.java)
                            emit(MyResult.Message<SendMoneyResponse>(message.message!!))
                        }
                    }
                }
            } catch (e: IOException) {
                emit(MyResult.Error<SendMoneyResponse>(e))
            }
        }

    override fun fee(feeRequest: FeeRequest): LiveData<MyResult<Double>> = liveData {
        try {
            val response =
                transferApi.fee(feeRequest.sender, feeRequest.amount)
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    emit(MyResult.Success<Double>(it))
                    return@liveData
                }
                emit(MyResult.Message<Double>("server bilan bog'lanishda xatolik"))
                return@liveData
            } else {
                when {
                    response.code() == 401 -> emit(MyResult.Logout<Double>())
                    response.code()==400 -> {
                        response.errorBody()?.let {
                            val message = Gson().fromJson(it.string(), ResponseData::class.java)
                            emit(MyResult.Message<Double>("401"))
                        }
                    }
                    else -> {
                        response.errorBody()?.let {
                            val message = Gson().fromJson(it.string(), ResponseData::class.java)
                            emit(MyResult.Message<Double>(message.message!!))
                        }
                    }
                }
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Double>(e))
        }
    }

    override suspend fun getHistory(scope: CoroutineScope): LiveData<PagingData<HistoryItem>> =
        Pager(
            PagingConfig(10)
        ) {
            HistoryDataSource(transferApi, cardApi)
        }.liveData.cachedIn(scope)

    override suspend fun outcomes(scope: CoroutineScope): LiveData<PagingData<HistoryItem>> = Pager(
        PagingConfig(10)
    ) {
        OutcomesDataSource(transferApi, cardApi)
    }.liveData.cachedIn(scope)

    override suspend fun incomes(scope: CoroutineScope): LiveData<PagingData<HistoryItem>> = Pager(
        PagingConfig(10)
    ) {
        IncomesDataSource(transferApi, cardApi)
    }.liveData.cachedIn(scope)
}