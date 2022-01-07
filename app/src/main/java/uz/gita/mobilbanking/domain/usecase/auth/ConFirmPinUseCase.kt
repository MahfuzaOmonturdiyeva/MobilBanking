package uz.gita.mobilbanking.domain.usecase.auth

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.LoginRequest

interface ConFirmPinUseCase {
    fun setPinLocal(pin: String)
}