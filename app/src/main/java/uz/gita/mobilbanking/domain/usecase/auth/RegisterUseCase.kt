package uz.gita.mobilbanking.domain.usecase.auth

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.RegisterRequest

interface RegisterUseCase {
    fun register(registerRequest: RegisterRequest): LiveData<MyResult<Unit>>
}