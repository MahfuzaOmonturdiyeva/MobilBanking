package uz.gita.mobilbanking.viewmodel.setting

import androidx.lifecycle.LiveData

interface SettingViewModel1 {

    val openLoginLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val phoneNumber: String
    val logoutLiveData: LiveData<Unit>

    fun logout()
}