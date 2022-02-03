package uz.gita.mobilbanking.viewmodel.card

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.request.AddCardRequest

interface AddCardViewModel {
    val openCardVerifyLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val messageLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    var favoriteCardId:Int
    val logoutLiveData:LiveData<Unit>

    fun addOneCard(addCardRequest: AddCardRequest)
}