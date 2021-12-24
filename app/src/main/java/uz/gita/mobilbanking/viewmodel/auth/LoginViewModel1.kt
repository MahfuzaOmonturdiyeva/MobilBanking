package uz.gita.mobilbanking.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.mobilbanking.data.request.LoginRequest

interface LoginViewModel1 {
    val openVerifyLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>

    fun login(loginRequest: LoginRequest)
}