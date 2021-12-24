package uz.gita.mobilbanking.ui.screenMainIshlatilmagani.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.domain.AuthRepository
import uz.gita.mobilbanking.utils.isConnected

class MainClickVersionCardViewModel : ViewModel() {
//    private val authRepository = AuthRepository()
//    val successLogoutLiveData = MutableLiveData<Unit>()
//    val errorLiveData = MutableLiveData<String>()
//    val notConnectionLiveData = MutableLiveData<String>()
//    val progressLiveData = MutableLiveData<Boolean>()
//
//    fun logout() {
//        if (!isConnected()) {
//            notConnectionLiveData.value = "Internet mavjud emas"
//            return
//        }
//        progressLiveData.value = true
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = authRepository.userLogout()
//            progressLiveData.postValue(false)
//            when (result) {
//                is MyResult.Success -> {
//                    successLogoutLiveData.postValue(Unit)
//                }
//                is MyResult.Error -> {
//                    errorLiveData.postValue(result.error.message)
//                }
//
//                is MyResult.Message -> {
//                    errorLiveData.postValue(result.data)
//                }
//            }
//        }
//    }
}