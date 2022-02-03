package uz.gita.mobilbanking.domain.usecase.onCreated.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.source.local.LocalStorage
import uz.gita.mobilbanking.domain.repository.AuthRepository
import uz.gita.mobilbanking.domain.usecase.onCreated.PinUseCase
import javax.inject.Inject

class PinUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : PinUseCase {

    override fun isCorrectPin(pin: String): Boolean = authRepository.getPin == pin

    override fun logout(): LiveData<MyResult<Unit>> = authRepository.userLogout()

}