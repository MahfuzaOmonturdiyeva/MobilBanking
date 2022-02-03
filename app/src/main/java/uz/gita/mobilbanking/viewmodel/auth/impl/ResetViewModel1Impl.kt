package uz.gita.mobilbanking.viewmodel.auth.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.NewPasswordRequest
import uz.gita.mobilbanking.domain.usecase.auth.ResetUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.auth.ResetViewModel1
import javax.inject.Inject

@HiltViewModel
class ResetViewModel1Impl @Inject constructor(
    private val resetUseCase: ResetUseCase
) : ResetViewModel1, ViewModel() {
    override val visibilityNewPasswordLiveData = MediatorLiveData<Unit>()
    override val openLoginLiveData = MediatorLiveData<Unit>()
    override val errorLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()

    override fun reset(phone: String) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            visibilityNewPasswordLiveData.addSource(resetUseCase.reset(phone)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> visibilityNewPasswordLiveData.value = Unit
                    is MyResult.Message -> errorLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->{}
                }
            }
        }
    }

    override fun newPassword(newPasswordRequest: NewPasswordRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            visibilityNewPasswordLiveData.addSource(resetUseCase.newPassword(newPasswordRequest)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> openLoginLiveData.value = Unit
                    is MyResult.Message -> errorLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout->{}
                }
            }
        }
    }
}