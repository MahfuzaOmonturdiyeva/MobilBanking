package uz.gita.mobilbanking.domain.usecase.auth.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.LoginRequest
import uz.gita.mobilbanking.data.request.VerifyRequest
import uz.gita.mobilbanking.domain.repository.AuthRepository
import uz.gita.mobilbanking.domain.usecase.auth.VerifyUseCase
import javax.inject.Inject

class VerifyUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : VerifyUseCase {
    override fun verify(verifyRequest: VerifyRequest): LiveData<MyResult<Unit>> =
        authRepository.userVerify(verifyRequest)

    override fun resend(loginRequest: LoginRequest): LiveData<MyResult<Unit>> =
        authRepository.userResend(loginRequest)
}