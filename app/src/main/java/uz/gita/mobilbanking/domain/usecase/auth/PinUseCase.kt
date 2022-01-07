package uz.gita.mobilbanking.domain.usecase.auth

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.LoginRequest

interface PinUseCase {
    fun pin(pin: String): LiveData<Boolean>
}