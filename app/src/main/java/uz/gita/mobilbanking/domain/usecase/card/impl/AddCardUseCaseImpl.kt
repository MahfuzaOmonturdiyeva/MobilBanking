package uz.gita.mobilbanking.domain.usecase.card.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.AddCardRequest
import uz.gita.mobilbanking.domain.repository.CardRepository
import uz.gita.mobilbanking.domain.repository.ProfileRepository
import uz.gita.mobilbanking.domain.usecase.card.AddCardUseCase
import javax.inject.Inject

class AddCardUseCaseImpl @Inject constructor(
    private val cardRepository: CardRepository
): AddCardUseCase {
    override var favoriteCardId: Int
        get()=cardRepository.favoriteCardId
        set(value) {cardRepository.favoriteCardId=value}

    override fun addOneCard(addCardRequest: AddCardRequest): LiveData<MyResult<Unit>> =cardRepository.addCard(addCardRequest)
}