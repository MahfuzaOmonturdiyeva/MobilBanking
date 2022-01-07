package uz.gita.mobilbanking.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.mobilbanking.data.request.LoginRequest
import uz.gita.mobilbanking.data.request.NewPasswordRequest
import uz.gita.mobilbanking.data.request.RegisterRequest

interface ResetViewModel1 {
    val visibilityNewPasswordLiveData: LiveData<Unit>
    val openLoginLiveData: LiveData<Unit>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>

    fun reset(phone: String)

    fun newPassword(newPasswordRequest: NewPasswordRequest)
}