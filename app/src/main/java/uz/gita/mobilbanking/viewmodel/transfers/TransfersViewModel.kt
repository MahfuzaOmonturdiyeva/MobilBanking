package uz.gita.mobilbanking.viewmodel.transfers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uz.gita.mobilbanking.data.request.FeeRequest
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.data.response.SendMoneyResponse

interface TransfersViewModel {
    val successSendMoneyLiveData: LiveData<SendMoneyResponse>
    val successFeeLiveData: LiveData<Double>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val messageLiveData: LiveData<String>
    val allCardsLiveData:LiveData<List<CardInfoResponse>>
    val senderCardLiveData:LiveData<CardInfoResponse>
    val senderIdLiveData:LiveData<Int>
    val receiverPanWithMyCards:LiveData<String>
    val allCardNotSenderLiveData:LiveData<List<CardInfoResponse>>
    val logoutLiveData:LiveData<Unit>

    fun getSenderCardId():Int
    fun getSenderCard()
    fun getAllCard()
    fun getAllCardNotSender()
    fun sendMoney(data:SendMoneyRequest)
    fun fee(feeRequest: FeeRequest)
    fun setReceiverPan(pan: String)
    fun setSenderId(id: Int)
    val ownerCardLiveData: LiveData<String>
    fun getCardOwnerByPan(pan: String)
    val notEnoughMoneyLivedata: LiveData<String>
    val saverPanLiveData: LiveData<String>
    val amountLiveData: LiveData<String>
    fun setAmount(pan: String)
    fun setSaverPan(pan: String)
    val notFoundOwnerCardLiveData: LiveData<String>
}