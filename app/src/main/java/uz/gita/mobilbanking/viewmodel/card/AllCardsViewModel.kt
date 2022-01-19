package uz.gita.mobilbanking.viewmodel.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.data.response.ColorResponse
import uz.gita.mobilbanking.data.response.IgnoreBalanceResponse

interface AllCardsViewModel {
    val joinAllCardsLiveData: LiveData<List<CardInfoResponse>>
    val errorLiveData: LiveData<String>
    val messageLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val successDeleteCardLiveData:LiveData<Unit>
    val successEditCardLiveData:LiveData<Unit>
    val successColorCardLiveData:LiveData<ColorResponse>
    val successIgnoreBalanceLiveData:LiveData<IgnoreBalanceResponse>
    val provideCardInfoResponseLiveData: MutableLiveData<CardInfoResponse>
    val favoriteCardId:Int

    fun deleteCard(cardNumberRequest: CardNumberRequest)
    fun editCard(editCardRequest: EditCardRequest)
    fun colorCard(colorRequest: ColorRequest)
    fun ignoreBalance(ignoreBalanceRequest: IgnoreBalanceRequest)
    fun getAllCard()
    fun setValueCardInfoResponse(value: CardInfoResponse)
    fun getCardInfoResponse(): CardInfoResponse?
}