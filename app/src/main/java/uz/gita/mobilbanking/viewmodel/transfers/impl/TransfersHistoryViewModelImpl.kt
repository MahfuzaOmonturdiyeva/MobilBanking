package uz.gita.mobilbanking.viewmodel.transfers.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.response.HistoryItem
import uz.gita.mobilbanking.domain.usecase.transfer.TransfersHistoryUseCase
import uz.gita.mobilbanking.viewmodel.transfers.TransfersHistoryViewModel
import javax.inject.Inject

@HiltViewModel
class TransfersHistoryViewModelImpl @Inject constructor(
    private val transfersHistoryUseCase: TransfersHistoryUseCase
) : TransfersHistoryViewModel, ViewModel() {
    override val setHistoryLiveData = MediatorLiveData<PagingData<HistoryItem>>()
    override val setOutComesLiveData = MediatorLiveData<PagingData<HistoryItem>>()
    override val setInComesLiveData = MediatorLiveData<PagingData<HistoryItem>>()
    override val errorLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()
    override val messageLiveData = MediatorLiveData<String>()

    init {
        getHistory()
    }
    override fun getHistory() {
        progressLiveData.value=true
        viewModelScope.launch {
            progressLiveData.addSource(transfersHistoryUseCase.getHistory(viewModelScope)) {
                setHistoryLiveData.value = it
                progressLiveData.value=false
            }
        }
    }

    override fun getOutcomes() {
        progressLiveData.value=true
        viewModelScope.launch {
            progressLiveData.addSource(transfersHistoryUseCase.outcomes(viewModelScope)) {
                setOutComesLiveData.value = it
                progressLiveData.value=false
            }
        }
    }

    override fun getIncomes() {
        progressLiveData.value=true
        viewModelScope.launch {
            progressLiveData.addSource(transfersHistoryUseCase.incomes(viewModelScope)) {
                setInComesLiveData.value = it
                progressLiveData.value=false
            }
        }
    }
}