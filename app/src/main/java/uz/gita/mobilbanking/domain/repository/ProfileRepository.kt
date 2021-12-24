package uz.gita.mobilbanking.domain.repository

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.ProfileRequest
import uz.gita.mobilbanking.data.response.ProfileInfoResponse

interface ProfileRepository {
    fun setUserAvatar(formData: String): LiveData<MyResult<Unit>>

    fun getUserAvatar(): LiveData<MyResult<Unit>>

    fun setUserProfileInfo(profileRequest: ProfileRequest): LiveData<MyResult<ProfileInfoResponse>>

    fun getUserProfileInfo(): LiveData<MyResult<ProfileInfoResponse>>

}