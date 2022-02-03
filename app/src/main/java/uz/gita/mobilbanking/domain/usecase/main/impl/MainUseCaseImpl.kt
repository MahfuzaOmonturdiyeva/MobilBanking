package uz.gita.mobilbanking.domain.usecase.main.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.domain.repository.CardRepository
import uz.gita.mobilbanking.domain.usecase.main.MainUseCase
import javax.inject.Inject

class MainUseCaseImpl @Inject constructor(
    private val cardRepository: CardRepository
) : MainUseCase {
    override var ignoreTotalSum: Boolean
        get() = cardRepository.ignoreTotalSum
        set(value) {
            cardRepository.ignoreTotalSum = value
        }
    override var favoriteCardId: Int
        get() = cardRepository.favoriteCardId
        set(value) {
            cardRepository.favoriteCardId
        }

    override fun getFavoriteCard(): LiveData<MyResult<List<CardInfoResponse>>> =
        cardRepository.getAllCard()

    override fun getTotalSum(): LiveData<MyResult<Double>> = cardRepository.getTotalSum()
}