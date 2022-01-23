package uz.gita.mobilbanking.viewmodel.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.ProfileRequest
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import java.io.File

interface PersonalViewModel1 {
    val joinAvatarLiveData: LiveData<String>
    val successSetAvatarLiveData: LiveData<Unit>
    val joinInfoLiveData: LiveData<ProfileInfoResponse>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    val messageLiveData: LiveData<String>

    fun getAvatar()
    fun setAvatar(file:File)
    fun getInfo()
    fun setInfo(profileRequest: ProfileRequest)
    val notSuccessGetAvatarLiveData: MediatorLiveData<String>
}