package uz.gita.mobilbanking.domain.usecase.transfer

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.FeeRequest
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.data.response.SendMoneyResponse

interface TransfersUseCase {

    fun sendMoney(data:SendMoneyRequest): LiveData<MyResult<SendMoneyResponse>>

    fun fee(feeRequest: FeeRequest):LiveData<MyResult<Double>>
}