package uz.gita.mobilbanking.domain.usecase.auth.impl

import uz.gita.mobilbanking.domain.repository.AuthRepository
import uz.gita.mobilbanking.domain.usecase.auth.ConFirmPinUseCase
import javax.inject.Inject


class ConFirmPinUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : ConFirmPinUseCase {
    override fun setPinLocal(pin: String)=authRepository.setPin(pin)
}