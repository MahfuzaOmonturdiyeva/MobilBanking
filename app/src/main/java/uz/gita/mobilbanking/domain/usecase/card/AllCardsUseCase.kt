package uz.gita.mobilbanking.domain.usecase.card

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.CardNumberRequest
import uz.gita.mobilbanking.data.request.ColorRequest
import uz.gita.mobilbanking.data.request.EditCardRequest
import uz.gita.mobilbanking.data.request.IgnoreBalanceRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.data.response.ColorResponse
import uz.gita.mobilbanking.data.response.IgnoreBalanceResponse

interface AllCardsUseCase {
    var favoriteCardId: Int
    fun getAllCard(): LiveData<MyResult<List<CardInfoResponse>>>
    fun deleteCard(cardNumberRequest: CardNumberRequest):LiveData<MyResult<Unit>>
    fun editCard(editCardRequest: EditCardRequest):LiveData<MyResult<Unit>>
    fun colorCard(colorRequest: ColorRequest):LiveData<MyResult<ColorResponse>>
    fun ignoreBalance(ignoreBalanceRequest: IgnoreBalanceRequest):LiveData<MyResult<IgnoreBalanceResponse>>
}