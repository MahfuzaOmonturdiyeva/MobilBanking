package uz.gita.mobilbanking.domain.usecase.auth

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.LoginRequest

interface LoginUseCase {
    fun login(loginRequest: LoginRequest): LiveData<MyResult<Unit>>
}