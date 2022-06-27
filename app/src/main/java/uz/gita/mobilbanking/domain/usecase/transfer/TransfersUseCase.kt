package uz.gita.mobilbanking.domain.usecase.transfer

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.FeeRequest
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.data.response.OwnerCardResponse
import uz.gita.mobilbanking.data.response.SendMoneyResponse

interface TransfersUseCase {
    val favoriteCardId: Int

    fun getAllCards(): LiveData<MyResult<List<CardInfoResponse>>>

    fun sendMoney(data: SendMoneyRequest): LiveData<MyResult<SendMoneyResponse>>

    fun fee(feeRequest: FeeRequest): LiveData<MyResult<Double>>
    fun getCardOwnerByPan(pan: String): LiveData<MyResult<OwnerCardResponse>>
}