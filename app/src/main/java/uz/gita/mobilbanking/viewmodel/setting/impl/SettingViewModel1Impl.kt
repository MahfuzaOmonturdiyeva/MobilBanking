package uz.gita.mobilbanking.viewmodel.setting.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.domain.usecase.setting.SettingsUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.setting.SettingViewModel1
import javax.inject.Inject

@HiltViewModel
class SettingViewModel1Impl @Inject constructor(
    private val settingsUseCase: SettingsUseCase
) : SettingViewModel1, ViewModel() {
    override val openLoginLiveData = MediatorLiveData<Unit>()
    override val errorLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()
    override val phoneNumber: String
        get() = settingsUseCase.getPhoneNumber

    override fun logout() {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            openLoginLiveData.addSource(settingsUseCase.logout()) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> {
                        openLoginLiveData.value = Unit
                    }
                    is MyResult.Error -> {
                        errorLiveData.value = it.error.toString()
                    }
                    is MyResult.Message -> {
                        openLoginLiveData.value = Unit
                    }
                }
            }
        }
    }
}