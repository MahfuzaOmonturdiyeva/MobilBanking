package uz.gita.mobilbanking.domain.usecase.auth.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.NewPasswordRequest
import uz.gita.mobilbanking.domain.repository.AuthRepository
import uz.gita.mobilbanking.domain.usecase.auth.ResetUseCase
import javax.inject.Inject

class ResetUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : ResetUseCase {
    override fun reset(phone: String): LiveData<MyResult<Unit>> = authRepository.userReset(phone)

    override fun newPassword(newPasswordRequest: NewPasswordRequest): LiveData<MyResult<Unit>> =
        authRepository.userNewPassword(newPasswordRequest)
}