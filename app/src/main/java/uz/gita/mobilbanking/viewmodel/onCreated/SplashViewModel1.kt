package uz.gita.mobilbanking.viewmodel.onCreated

import androidx.lifecycle.LiveData


interface SplashViewModel1 {
    val openPinLiveData: LiveData<Unit>
    val openLoginLiveData: LiveData<Unit>
    val notConnectionLiveData: LiveData<String>
}