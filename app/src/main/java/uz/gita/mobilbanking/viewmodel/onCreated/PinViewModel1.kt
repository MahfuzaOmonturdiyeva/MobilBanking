package uz.gita.mobilbanking.viewmodel.onCreated

import androidx.lifecycle.LiveData

interface PinViewModel1 {
    val openLoginLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val logoutLiveData: LiveData<Unit>

    fun logout()
    fun isCorrectPin(pin: String): Boolean
}