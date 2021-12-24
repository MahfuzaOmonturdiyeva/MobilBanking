package uz.gita.mobilbanking.domain.usecase.auth

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.LoginRequest
import uz.gita.mobilbanking.data.request.VerifyRequest

interface VerifyUseCase {
    fun verify(verifyRequest: VerifyRequest): LiveData<MyResult<Unit>>
    fun resend(loginRequest: LoginRequest): LiveData<MyResult<Unit>>
}