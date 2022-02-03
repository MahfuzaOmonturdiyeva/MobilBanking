package uz.gita.mobilbanking.viewmodel.card.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.AddCardRequest
import uz.gita.mobilbanking.domain.usecase.card.AddCardUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.card.AddCardViewModel
import javax.inject.Inject

@HiltViewModel
class AddCardViewModelImpl @Inject constructor(
    private val addCardUseCase: AddCardUseCase
): AddCardViewModel, ViewModel() {
    override val openCardVerifyLiveData =MediatorLiveData<Unit>()
    override val errorLiveData=MediatorLiveData<String>()
    override val messageLiveData=MediatorLiveData<String>()
    override val notConnectionLiveData=MediatorLiveData<String>()
    override val progressLiveData=MediatorLiveData<Boolean>()
    override val logoutLiveData=MediatorLiveData<Unit>()
    override var favoriteCardId: Int
        get() =addCardUseCase.favoriteCardId
        set(value) {addCardUseCase.favoriteCardId=value}


    override fun addOneCard(addCardRequest: AddCardRequest) {
        viewModelScope.launch {
            if(!isConnected()){
                notConnectionLiveData.value="Internet not connection"
            }
            progressLiveData.value=true
            openCardVerifyLiveData.addSource(addCardUseCase.addOneCard(addCardRequest)){
                progressLiveData.value=false
                when(it){
                    is MyResult.Success-> openCardVerifyLiveData.value=Unit
                    is MyResult.Message-> messageLiveData.value=it.data
                    is MyResult.Error ->errorLiveData.value=it.error.toString()
                    is MyResult.Logout->{logoutLiveData.value=Unit}
                }
            }
        }
    }
}