package uz.gita.mobilbanking.domain.usecase.setting

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult

interface SettingsUseCase {
    val getPhoneNumber: String
    fun logout(): LiveData<MyResult<Unit>>
}