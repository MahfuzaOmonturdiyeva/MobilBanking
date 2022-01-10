package uz.gita.mobilbanking.domain.repository

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.data.response.ColorResponse
import uz.gita.mobilbanking.data.response.IgnoreBalanceResponse
import uz.gita.mobilbanking.data.response.OwnerCardResponse

interface CardRepository {
    var favoriteCardId:Int
    var ignoreTotalSum:Boolean
    fun addCard(addCardRequest: AddCardRequest): LiveData<MyResult<Unit>>

    fun addCardVerify(addCardVerifyRequest: AddCardVerifyRequest): LiveData<MyResult<CardInfoResponse>>

    fun editCard(editCardRequest: EditCardRequest): LiveData<MyResult<Unit>>

    fun deleteCard(cardNumberRequest: CardNumberRequest): LiveData<MyResult<Unit>>

    fun getAllCard(): LiveData<MyResult<List<CardInfoResponse>>>

    fun getTotalSum(): LiveData<MyResult<Double>>

    fun ownerByPan(ownerByPanRequest: OwnerByPanRequest): LiveData<MyResult<OwnerCardResponse>>

    fun ownerById( ownerByIdRequest: OwnerByIdRequest): LiveData<MyResult<OwnerCardResponse>>

    fun colorCard(colorRequest: ColorRequest): LiveData<MyResult<ColorResponse>>

    fun ignoreBalance(ignoreBalanceRequest: IgnoreBalanceRequest): LiveData<MyResult<IgnoreBalanceResponse>>//holi yozmadim
}