package uz.gita.mobilbanking.viewmodel.setting.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.ProfileRequest
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import uz.gita.mobilbanking.domain.usecase.setting.PersonalUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.setting.PersonalViewModel1
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel1Impl @Inject constructor(
    private val personalUseCase: PersonalUseCase
) : PersonalViewModel1, ViewModel() {

    override val joinAvatarLiveData = MediatorLiveData<String>()
    override val notSuccessGetAvatarLiveData = MediatorLiveData<String>()
    override val successSetAvatarLiveData = MediatorLiveData<Unit>()
    override val joinInfoLiveData = MediatorLiveData<ProfileInfoResponse>()
    override val errorLiveData = MediatorLiveData<String>()
    override val messageLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()

    init {
      //  getAvatar()
        getInfo()
    }

    override fun getAvatar() {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            joinAvatarLiveData.addSource(personalUseCase.getAvatar()) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> joinAvatarLiveData.value = it.data!!
                    is MyResult.Message -> notSuccessGetAvatarLiveData.value=it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                }
            }
        }
    }

    override fun setAvatar(file: File) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            successSetAvatarLiveData.addSource(personalUseCase.setAvatar(file)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> successSetAvatarLiveData.value = Unit
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                }
            }
        }
    }

    override fun getInfo() {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            joinInfoLiveData.addSource(personalUseCase.getInfo()) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> joinInfoLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                }
            }
        }
    }

    override fun setInfo(profileRequest: ProfileRequest) {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            joinInfoLiveData.addSource(personalUseCase.setInfo(profileRequest)) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> joinInfoLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                }
            }
        }
    }
}