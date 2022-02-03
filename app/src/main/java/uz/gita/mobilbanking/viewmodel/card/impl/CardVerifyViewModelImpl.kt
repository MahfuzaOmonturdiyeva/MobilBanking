package uz.gita.mobilbanking.viewmodel.card.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.AddCardVerifyRequest
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.domain.usecase.card.CardVerifyUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.card.CardVerifyViewModel
import javax.inject.Inject

@HiltViewModel
class CardVerifyViewModelImpl @Inject constructor(
    private val cardVerifyUseCase: CardVerifyUseCase
) : CardVerifyViewModel, ViewModel() {
    override val successVerifyLiveData = MediatorLiveData<CardInfoResponse>()
    override val errorLiveData = MediatorLiveData<String>()
    override val messageLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()
    override val logoutLiveData=MediatorLiveData<Unit>()
    override var favoriteCardId: Int
        get() = cardVerifyUseCase.favoriteCardId
        set(value) {
            cardVerifyUseCase.favoriteCardId = value
        }


    override fun addCardVerify(cardVerifyRequest: AddCardVerifyRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            successVerifyLiveData.addSource(cardVerifyUseCase.addCardVerify(cardVerifyRequest)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> successVerifyLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->logoutLiveData.value=Unit
                }
            }
        }
    }
}