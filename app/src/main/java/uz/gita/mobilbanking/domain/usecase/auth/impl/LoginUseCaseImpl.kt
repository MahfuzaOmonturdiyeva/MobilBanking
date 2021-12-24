package uz.gita.mobilbanking.domain.usecase.auth.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.LoginRequest
import uz.gita.mobilbanking.domain.repository.AuthRepository
import uz.gita.mobilbanking.domain.usecase.auth.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : LoginUseCase {
    override fun login(loginRequest: LoginRequest): LiveData<MyResult<Unit>> =
        authRepository.userLogin(loginRequest)
}