package uz.gita.mobilbanking.domain.usecase.setting.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.source.local.LocalStorage
import uz.gita.mobilbanking.domain.repository.AuthRepository
import uz.gita.mobilbanking.domain.usecase.setting.SettingsUseCase
import javax.inject.Inject

class SettingsUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SettingsUseCase {

    override val getPhoneNumber: String
        get() = authRepository.getPhoneNumber

    override fun logout(): LiveData<MyResult<Unit>> = authRepository.userLogout()
}