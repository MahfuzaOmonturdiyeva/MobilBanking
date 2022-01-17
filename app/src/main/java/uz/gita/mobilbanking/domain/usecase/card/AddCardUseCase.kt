package uz.gita.mobilbanking.domain.usecase.card

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.AddCardRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse

interface AddCardUseCase {
    var favoriteCardId: Int
    fun addOneCard(addCardRequest: AddCardRequest): LiveData<MyResult<Unit>>
}