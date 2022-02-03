package uz.gita.mobilbanking.domain.usecase.card.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.CardNumberRequest
import uz.gita.mobilbanking.data.request.ColorRequest
import uz.gita.mobilbanking.data.request.EditCardRequest
import uz.gita.mobilbanking.data.request.IgnoreBalanceRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.data.response.ColorResponse
import uz.gita.mobilbanking.data.response.IgnoreBalanceResponse
import uz.gita.mobilbanking.domain.repository.CardRepository
import uz.gita.mobilbanking.domain.usecase.card.AllCardsUseCase
import javax.inject.Inject

class AllCardsUseCaseImpl @Inject constructor(
    private val cardRepository: CardRepository
) : AllCardsUseCase {
    override var favoriteCardId: Int
        get() = cardRepository.favoriteCardId
        set(value) {
            cardRepository.favoriteCardId = value
        }

    override fun getAllCard(): LiveData<MyResult<List<CardInfoResponse>>> =
        cardRepository.getAllCard()

    override fun deleteCard(cardNumberRequest: CardNumberRequest): LiveData<MyResult<Unit>> =
        cardRepository.deleteCard(cardNumberRequest)

    override fun editCard(editCardRequest: EditCardRequest): LiveData<MyResult<Unit>> =
        cardRepository.editCard(editCardRequest)

    override fun colorCard(colorRequest: ColorRequest): LiveData<MyResult<ColorResponse>> =
        cardRepository.colorCard(colorRequest)

    override fun ignoreBalance(ignoreBalanceRequest: IgnoreBalanceRequest): LiveData<MyResult<IgnoreBalanceResponse>> =
        cardRepository.ignoreBalance(ignoreBalanceRequest)
}