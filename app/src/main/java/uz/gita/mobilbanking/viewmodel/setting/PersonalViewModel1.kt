package uz.gita.mobilbanking.viewmodel.setting

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.ProfileRequest
import uz.gita.mobilbanking.data.response.ProfileInfoResponse

interface PersonalViewModel1 {
    val joinAvatarLiveData : LiveData<Unit>
    val successSetAvatarLiveData : LiveData<Unit>
    val joinInfoLiveData : LiveData<ProfileInfoResponse>
    val errorLiveData : LiveData<String>
    val notConnectionLiveData : LiveData<String>
    val progressLiveData:LiveData<Boolean>

    fun getAvatar()
    fun setAvatar(formdata:String)
    fun getInfo()
    fun setInfo(profileRequest: ProfileRequest)
}