package uz.gita.mobilbanking.domain.usecase.onCreated

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult

interface SplashUseCase {
    fun check(): LiveData<MyResult<Unit>>
}