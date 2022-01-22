package uz.gita.mobilbanking.viewmodel.transfers

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import uz.gita.mobilbanking.data.response.HistoryItem

interface TransfersHistoryViewModel {
    val setHistoryLiveData: LiveData<PagingData<HistoryItem>>
    val setOutComesLiveData: LiveData<PagingData<HistoryItem>>
    val setInComesLiveData: LiveData<PagingData<HistoryItem>>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val messageLiveData: LiveData<String>

    fun getHistory()
    fun getOutcomes()
    fun getIncomes()
}