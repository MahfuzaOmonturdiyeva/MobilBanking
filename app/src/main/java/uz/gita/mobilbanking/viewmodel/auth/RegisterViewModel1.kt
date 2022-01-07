package uz.gita.mobilbanking.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.mobilbanking.data.request.LoginRequest
import uz.gita.mobilbanking.data.request.RegisterRequest

interface RegisterViewModel1 {
    val openVerifyLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>

    fun register(registerRequest: RegisterRequest)
}