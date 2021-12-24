package uz.gita.mobilbanking.domain.usecase.auth

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.NewPasswordRequest

interface ResetUseCase {
    fun reset(phone:String):LiveData<MyResult<Unit>>

    fun newPassword(newPasswordRequest: NewPasswordRequest):LiveData<MyResult<Unit>>
}