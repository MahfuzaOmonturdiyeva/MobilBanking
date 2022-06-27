package uz.gita.mobilbanking.viewmodel.transfers.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.FeeRequest
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.data.response.SendMoneyResponse
import uz.gita.mobilbanking.domain.usecase.transfer.TransfersUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.transfers.TransfersViewModel
import javax.inject.Inject

@HiltViewModel
class TransfersViewModelImpl @Inject constructor(
    private val transfersUseCase: TransfersUseCase
) : TransfersViewModel, ViewModel() {
    private var isSenderNull=true
    override val successSendMoneyLiveData = MediatorLiveData<SendMoneyResponse>()
    override val successFeeLiveData = MediatorLiveData<Double>()
    override val errorLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()
    override val messageLiveData = MediatorLiveData<String>()
    override val allCardsLiveData = MediatorLiveData<List<CardInfoResponse>>()
    override val senderCardLiveData=MediatorLiveData<CardInfoResponse>()
    override val senderIdLiveData = MediatorLiveData<Int>()
    override val receiverPanWithMyCards = MediatorLiveData<String>()
    override val allCardNotSenderLiveData=MediatorLiveData<List<CardInfoResponse>>()
    override val logoutLiveData=MediatorLiveData<Unit>()
    override val ownerCardLiveData=MediatorLiveData<String>()
    override val notFoundOwnerCardLiveData=MediatorLiveData<String>()
    override val notEnoughMoneyLivedata=MediatorLiveData<String>()
    override val saverPanLiveData=MediatorLiveData<String>()
    override val amountLiveData=MediatorLiveData<String>()

    override fun setSenderId(id:Int){
        viewModelScope.launch {
            isSenderNull=false
            senderIdLiveData.value=id
        }
    }
    override fun setSaverPan(pan:String){
        viewModelScope.launch {
            saverPanLiveData.value=pan
        }
    }

    override fun setAmount(amount:String){
        viewModelScope.launch {
            amountLiveData.value=amount
        }
    }
    override fun setReceiverPan(pan:String) {
        viewModelScope.launch {
            receiverPanWithMyCards.value=pan
        }
    }
    override fun getSenderCardId(): Int {
        if(isSenderNull)
        senderIdLiveData.value = transfersUseCase.favoriteCardId
        return senderIdLiveData.value!!
    }

    override fun getSenderCard() {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            senderCardLiveData.addSource(transfersUseCase.getAllCards()) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> {
                        it.data.let {list->
                            val id=senderIdLiveData.value
                            id?.let {id->
                                for (i in list){
                                    if(i.id==id){
                                        senderCardLiveData.value=i
                                    }
                                }
                            }
                        }
                    }
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }

    override fun getAllCard() {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            allCardsLiveData.addSource(transfersUseCase.getAllCards()) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> allCardsLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }

    override fun getCardOwnerByPan(pan:String) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            ownerCardLiveData.addSource(transfersUseCase.getCardOwnerByPan(pan)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> ownerCardLiveData.value=it.data.fio
                    is MyResult.Message -> {
                        if(it.data=="401"){
                            notFoundOwnerCardLiveData.value="The card is not available"
                        }else
                        messageLiveData.value = it.data}
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }

    override fun getAllCardNotSender() {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            allCardNotSenderLiveData.addSource(transfersUseCase.getAllCards()) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> {
                        val arrayList=ArrayList<CardInfoResponse>()
                        it.data.let {list->
                            val id=senderIdLiveData.value
                            id?.let {id->
                                for (i in list){
                                    if(i.id!=id){
                                        arrayList.add(i)
                                    }
                                }
                            }
                        }
                        allCardNotSenderLiveData.value=arrayList
                    }
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }

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
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }

    override fun fee(feeRequest: FeeRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            successFeeLiveData.addSource(transfersUseCase.fee(feeRequest)) {
                when (it) {
                    is MyResult.Success -> successFeeLiveData.value = it.data!!
                    is MyResult.Message -> {
                        if(it.data=="401")
                            notEnoughMoneyLivedata.value="Not enough money"
                        else
                        messageLiveData.value = it.data}
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }
}