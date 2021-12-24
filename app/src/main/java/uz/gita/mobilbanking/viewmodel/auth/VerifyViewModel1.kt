package uz.gita.mobilbanking.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.mobilbanking.data.request.LoginRequest
import uz.gita.mobilbanking.data.request.NewPasswordRequest
import uz.gita.mobilbanking.data.request.RegisterRequest
import uz.gita.mobilbanking.data.request.VerifyRequest

interface VerifyViewModel1 {
    val successResendLiveData: LiveData<Unit>
    val openNewPinLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>

    fun verify(verifyRequest: VerifyRequest)

    fun resend(loginRequest: LoginRequest)
}