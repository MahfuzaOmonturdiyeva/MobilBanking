package uz.gita.mobilbanking.domain.usecase.onCreated

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult

interface PinUseCase {
    fun isCorrectPin(pin: String): Boolean

    fun logout(): LiveData<MyResult<Unit>>
}