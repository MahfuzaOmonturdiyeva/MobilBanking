package uz.gita.mobilbanking.viewmodel.auth.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.LoginRequest
import uz.gita.mobilbanking.data.request.VerifyRequest
import uz.gita.mobilbanking.domain.usecase.auth.VerifyUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.auth.VerifyViewModel1
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel1Impl @Inject constructor(
    private val verifyUseCase: VerifyUseCase
) : VerifyViewModel1, ViewModel() {
    override val successResendLiveData = MediatorLiveData<Unit>()
    override val openNewPinLiveData = MediatorLiveData<Unit>()
    override val errorLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()

    override fun verify(verifyRequest: VerifyRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            openNewPinLiveData.addSource(verifyUseCase.verify(verifyRequest)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> openNewPinLiveData.value = Unit
                    is MyResult.Message -> errorLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                }
            }
        }
    }

    override fun resend(loginRequest: LoginRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            successResendLiveData.addSource(verifyUseCase.resend(loginRequest)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> successResendLiveData.value = Unit
                    is MyResult.Message -> errorLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                }
            }
        }
    }
}