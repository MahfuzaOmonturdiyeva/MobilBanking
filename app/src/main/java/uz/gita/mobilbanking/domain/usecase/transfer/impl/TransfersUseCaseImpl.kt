package uz.gita.mobilbanking.domain.usecase.transfer.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.FeeRequest
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.data.response.SendMoneyResponse
import uz.gita.mobilbanking.domain.repository.CardRepository
import uz.gita.mobilbanking.domain.repository.TransferRepository
import uz.gita.mobilbanking.domain.usecase.transfer.TransfersUseCase
import javax.inject.Inject

class TransfersUseCaseImpl @Inject constructor(
    private val transferRepository: TransferRepository,
    private val cardRepository: CardRepository
) : TransfersUseCase {
    override val favoriteCardId: Int
        get() = cardRepository.favoriteCardId

    override fun getAllCards() = cardRepository.getAllCard()


    override fun sendMoney(data: SendMoneyRequest): LiveData<MyResult<SendMoneyResponse>> =
        transferRepository.sendMoney(data)

    override fun fee(feeRequest: FeeRequest): LiveData<MyResult<Double>> =
        transferRepository.fee(feeRequest)
}