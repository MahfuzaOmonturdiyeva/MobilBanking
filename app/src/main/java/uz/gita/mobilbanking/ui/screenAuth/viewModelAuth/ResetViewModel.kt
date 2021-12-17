package uz.gita.mobilbanking.ui.screenAuth.viewModelAuth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.local.MyResult
import uz.gita.mobilbanking.data.request.NewPasswordRequest
import uz.gita.mobilbanking.domain.AuthRepository
import uz.gita.mobilbanking.utils.isConnected

class ResetViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    val successResetLiveData = MutableLiveData<Unit>()
    val errorLiveData = MutableLiveData<String>()
    val notConnectionLiveData = MutableLiveData<String>()
    val progressLiveData = MutableLiveData<Boolean>()
    val successNewPasswordLiveData = MutableLiveData<Unit>()

    fun reset(phone: String) {
        if (!isConnected()) {
            notConnectionLiveData.value = "Internet mavjud emas"
            return
        }
        progressLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.userReset(phone)
            progressLiveData.postValue(false)
            when (result) {
                is MyResult.Success -> {
                    successResetLiveData.postValue(Unit)
                }
                is MyResult.Error -> {
                    errorLiveData.postValue(result.error.message)
                }

                is MyResult.Message -> {
                    errorLiveData.postValue(result.data)
                }
            }
        }
    }
    fun newPassword(data: NewPasswordRequest) {
        if (!isConnected()) {
            notConnectionLiveData.value = "Internet mavjud emas"
            return
        }
        progressLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.userNewPassword(data)
            progressLiveData.postValue(false)
            when (result) {
                is MyResult.Success -> {
                    successNewPasswordLiveData.postValue(Unit)
                }
                is MyResult.Error -> {
                    errorLiveData.postValue(result.error.message)
                }

                is MyResult.Message -> {
                    errorLiveData.postValue(result.data)
                }
            }
        }
    }
}