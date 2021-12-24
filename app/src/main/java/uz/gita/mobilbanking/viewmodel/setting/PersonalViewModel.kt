package uz.gita.mobilbanking.viewmodel.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.request.ProfileRequest
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.domain.ProfileRepository
import uz.gita.mobilbanking.utils.isConnected

class PersonalViewModel : ViewModel() {
//    private val userProfileRepository = ProfileRepository()
//    val successSetInfoLiveData = MutableLiveData<Unit>()
//    val successGetInfoLiveData = MutableLiveData<ProfileInfoResponse>()
//    val errorLiveData = MutableLiveData<String>()
//    val notConnectionLiveData = MutableLiveData<String>()
//    val progressLiveData = MutableLiveData<Boolean>()
//
//    fun setPersonalInfo(profileRequest: ProfileRequest) {
//        if (!isConnected()) {
//            notConnectionLiveData.value = "Internet mavjud emas"
//            return
//        }
//        progressLiveData.value = true
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = userProfileRepository.setUserProfileInfo(profileRequest)
//            progressLiveData.postValue(false)
//            when (result) {
//                is MyResult.Success -> {
//                    successSetInfoLiveData.postValue(Unit)
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
//
//    fun getPersonalInfo() {
//        if (!isConnected()) {
//            notConnectionLiveData.value = "Internet mavjud emas"
//            return
//        }
//        progressLiveData.value = true
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = userProfileRepository.getUserProfileInfo()
//            progressLiveData.postValue(false)
//            when (result) {
//                is MyResult.Success -> {
//                    successGetInfoLiveData.postValue(result.data!!)
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