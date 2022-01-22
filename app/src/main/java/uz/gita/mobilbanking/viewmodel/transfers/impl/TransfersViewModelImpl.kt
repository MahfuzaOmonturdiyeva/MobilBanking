package uz.gita.mobilbanking.viewmodel.transfers.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.FeeRequest
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.data.response.SendMoneyResponse
import uz.gita.mobilbanking.domain.usecase.transfer.TransfersUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.transfers.TransfersViewModel
import javax.inject.Inject

@HiltViewModel
class TransfersViewModelImpl @Inject constructor(
    private val transfersUseCase: TransfersUseCase
) : TransfersViewModel, ViewModel() {
    override val successSendMoneyLiveData = MediatorLiveData<SendMoneyResponse>()
    override val successFeeLiveData = MediatorLiveData<Double>()
    override val errorLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()
    override val messageLiveData = MediatorLiveData<String>()

    override fun sendMoney(data: SendMoneyRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            successSendMoneyLiveData.addSource(transfersUseCase.sendMoney(data)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> successSendMoneyLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                }
            }
        }
    }

    override fun fee(feeRequest: FeeRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            successFeeLiveData.addSource(transfersUseCase.fee(feeRequest)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> successFeeLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                }
            }
        }
    }
}