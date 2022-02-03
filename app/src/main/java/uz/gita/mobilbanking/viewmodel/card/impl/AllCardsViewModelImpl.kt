package uz.gita.mobilbanking.viewmodel.card.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.CardNumberRequest
import uz.gita.mobilbanking.data.request.ColorRequest
import uz.gita.mobilbanking.data.request.EditCardRequest
import uz.gita.mobilbanking.data.request.IgnoreBalanceRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.data.response.ColorResponse
import uz.gita.mobilbanking.data.response.IgnoreBalanceResponse
import uz.gita.mobilbanking.domain.usecase.card.AllCardsUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.card.AllCardsViewModel
import javax.inject.Inject

@HiltViewModel
class AllCardsViewModelImpl @Inject constructor(
    private val allCardsUseCase: AllCardsUseCase
) : AllCardsViewModel, ViewModel() {
    override val joinAllCardsLiveData = MediatorLiveData<List<CardInfoResponse>>()
    override val errorLiveData = MediatorLiveData<String>()
    override val messageLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()
    override val successDeleteCardLiveData = MediatorLiveData<Unit>()
    override val successEditCardLiveData = MediatorLiveData<Unit>()
    override val successColorCardLiveData = MediatorLiveData<ColorResponse>()
    override var successIgnoreBalanceLiveData = MediatorLiveData<IgnoreBalanceResponse>()
    override val logoutLiveData=MediatorLiveData<Unit>()
    override val provideCardInfoResponseLiveData: MutableLiveData<CardInfoResponse> by lazy {
        MutableLiveData<CardInfoResponse>()
    }

    override fun setValueCardInfoResponse(value:CardInfoResponse){
       viewModelScope.launch {
           provideCardInfoResponseLiveData.value=value
       }
    }

    override fun getCardInfoResponse():CardInfoResponse?{
        var cardInfoResponse:CardInfoResponse?=null
        viewModelScope.launch {
            cardInfoResponse= provideCardInfoResponseLiveData.value!!
        }
        return cardInfoResponse
    }
    override var favoriteCardId: Int
        get() = allCardsUseCase.favoriteCardId
        set(value) {
            allCardsUseCase.favoriteCardId = value
        }

    override fun getAllCard() {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            joinAllCardsLiveData.addSource(allCardsUseCase.getAllCard()) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> joinAllCardsLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }

    override fun deleteCard(cardNumberRequest: CardNumberRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            successDeleteCardLiveData.addSource(allCardsUseCase.deleteCard(cardNumberRequest)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> successDeleteCardLiveData.value = Unit
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }

    override fun editCard(editCardRequest: EditCardRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            successEditCardLiveData.addSource(allCardsUseCase.editCard(editCardRequest)) {
                when (it) {
                    is MyResult.Success -> successEditCardLiveData.value = Unit
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }

    override fun colorCard(colorRequest: ColorRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            successColorCardLiveData.addSource(allCardsUseCase.colorCard(colorRequest)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> successColorCardLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }

    override fun ignoreBalance(ignoreBalanceRequest: IgnoreBalanceRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            successIgnoreBalanceLiveData.addSource(
                allCardsUseCase.ignoreBalance(
                    ignoreBalanceRequest
                )
            ) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> successIgnoreBalanceLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }
}