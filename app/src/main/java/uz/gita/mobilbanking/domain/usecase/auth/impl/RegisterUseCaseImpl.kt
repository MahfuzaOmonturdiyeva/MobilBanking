package uz.gita.mobilbanking.domain.usecase.auth.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.RegisterRequest
import uz.gita.mobilbanking.domain.repository.AuthRepository
import uz.gita.mobilbanking.domain.usecase.auth.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : RegisterUseCase {
    override fun register(registerRequest: RegisterRequest): LiveData<MyResult<Unit>> =
        authRepository.userRegister(registerRequest)
}