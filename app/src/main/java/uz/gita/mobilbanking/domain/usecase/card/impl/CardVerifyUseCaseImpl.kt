package uz.gita.mobilbanking.domain.usecase.card.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.AddCardVerifyRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.domain.repository.CardRepository
import uz.gita.mobilbanking.domain.usecase.card.CardVerifyUseCase
import javax.inject.Inject

class CardVerifyUseCaseImpl @Inject constructor(
    private val cardRepository: CardRepository
) : CardVerifyUseCase {
    override var favoriteCardId: Int
        get() = cardRepository.favoriteCardId
        set(value) {
            cardRepository.favoriteCardId = value
        }

    override fun addCardVerify(addCardVerifyRequest: AddCardVerifyRequest): LiveData<MyResult<CardInfoResponse>> =
        cardRepository.addCardVerify(addCardVerifyRequest)
}