package uz.gita.mobilbanking.viewmodel.onCreated.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.domain.usecase.onCreated.PinUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.onCreated.PinViewModel1
import javax.inject.Inject

@HiltViewModel
class PinViewModel1Impl @Inject constructor(
    private val pinUseCase: PinUseCase
): PinViewModel1, ViewModel() {

    override val openLoginLiveData= MediatorLiveData<Unit>()
    override val errorLiveData= MediatorLiveData<String>()
    override val notConnectionLiveData= MediatorLiveData<String>()
    override val progressLiveData= MediatorLiveData<Boolean>()

    override fun logout() {
        viewModelScope.launch {
            if (!isConnected()){
                notConnectionLiveData.value="Internet not connection"
            }
            progressLiveData.value=true
            openLoginLiveData.addSource(pinUseCase.logout()){
                when(it){
                    is MyResult.Success -> openLoginLiveData.value=Unit
                    is MyResult.Message -> errorLiveData.value=it.data
                    is MyResult.Error -> errorLiveData.value=it.error.toString()
                }
            }
        }
    }

    override fun isCorrectPin(pin: String): Boolean =pinUseCase.isCorrectPin(pin)

}