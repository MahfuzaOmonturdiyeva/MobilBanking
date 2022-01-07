package uz.gita.mobilbanking.domain.usecase.setting

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.ProfileRequest
import uz.gita.mobilbanking.data.response.ProfileInfoResponse

interface PersonalUseCase {
    fun getAvatar(): LiveData<MyResult<Unit>>
    fun setAvatar(formdata: String): LiveData<MyResult<Unit>> // bu qismini bilmaganligim uchun shundoq turibdi
    fun getInfo(): LiveData<MyResult<ProfileInfoResponse>>
    fun setInfo(profileRequest: ProfileRequest): LiveData<MyResult<ProfileInfoResponse>>
}