package uz.gita.mobilbanking.domain.usecase.onCreated.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.domain.repository.AuthRepository
import uz.gita.mobilbanking.domain.usecase.onCreated.SplashUseCase
import javax.inject.Inject
import javax.inject.Singleton

class SplashUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SplashUseCase {

    override fun check(): LiveData<MyResult<Unit>> = authRepository.userRefreshToken()
}