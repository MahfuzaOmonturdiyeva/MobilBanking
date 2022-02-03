package uz.gita.mobilbanking.viewmodel.auth.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.RegisterRequest
import uz.gita.mobilbanking.domain.usecase.auth.RegisterUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.auth.RegisterViewModel1
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel1Impl @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : RegisterViewModel1, ViewModel() {
    override val openVerifyLiveData = MediatorLiveData<Unit>()
    override val errorLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()

    override fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            openVerifyLiveData.addSource(registerUseCase.register(registerRequest)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> openVerifyLiveData.value = Unit
                    is MyResult.Message -> errorLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->{}
                }
            }
        }
    }
}