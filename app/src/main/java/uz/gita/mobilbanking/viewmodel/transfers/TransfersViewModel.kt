package uz.gita.mobilbanking.viewmodel.transfers

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.request.FeeRequest
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.data.response.SendMoneyResponse

interface TransfersViewModel {
    val successSendMoneyLiveData: LiveData<SendMoneyResponse>
    val successFeeLiveData: LiveData<Double>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val messageLiveData: LiveData<String>

    fun sendMoney(data:SendMoneyRequest)
    fun fee(feeRequest: FeeRequest)
}