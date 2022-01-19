package uz.gita.mobilbanking.domain.usecase.card

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.AddCardRequest
import uz.gita.mobilbanking.data.request.AddCardVerifyRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse

interface CardVerifyUseCase {
    var favoriteCardId: Int
    fun addCardVerify(addCardVerifyRequest: AddCardVerifyRequest): LiveData<MyResult<CardInfoResponse>>
}