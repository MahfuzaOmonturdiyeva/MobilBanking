package uz.gita.mobilbanking.ui.screenOnCreated.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.local.MyResult
import uz.gita.mobilbanking.domain.AuthRepository
import uz.gita.mobilbanking.utils.isConnected

class SplashViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    val successPinLiveData = MutableLiveData<Unit>()
    val errorLiveData = MutableLiveData<String>()
    val notConnectionLiveData = MutableLiveData<String>()

    fun updateTokens() {
        if (!isConnected()) {
            notConnectionLiveData.value = "Internet mavjud emas"
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.userRefreshToken()

            when (result) {
                is MyResult.Success -> {
                    successPinLiveData.postValue(Unit)
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