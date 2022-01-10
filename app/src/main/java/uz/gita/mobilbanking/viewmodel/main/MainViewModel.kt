package uz.gita.mobilbanking.viewmodel.main

import androidx.lifecycle.LiveData

interface MainViewModel {
    val setTotalSumLiveData: LiveData<Double>
    val messageLiveData: LiveData<String>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    var ignoreTotalSum:Boolean
    var favoriteCardId:Int
}