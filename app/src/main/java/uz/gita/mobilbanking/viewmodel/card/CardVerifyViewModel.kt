package uz.gita.mobilbanking.viewmodel.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.AddCardRequest
import uz.gita.mobilbanking.data.request.AddCardVerifyRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse

interface CardVerifyViewModel {
    val successVerifyLiveData: LiveData<CardInfoResponse>
    val errorLiveData: LiveData<String>
    val messageLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    var favoriteCardId:Int
    val logoutLiveData: LiveData<Unit>

    fun addCardVerify(cardVerifyRequest: AddCardVerifyRequest)
}