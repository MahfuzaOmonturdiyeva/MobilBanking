package uz.gita.mobilbanking.viewmodel.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.ProfileRequest
import uz.gita.mobilbanking.data.response.ProfileInfoResponse

interface SettingViewModel1 {

    val openLoginLiveData : LiveData<Unit>
    val errorLiveData : LiveData<String>
    val notConnectionLiveData : LiveData<String>
    val progressLiveData:LiveData<Boolean>
    val phoneNumber:String

    fun logout()
}