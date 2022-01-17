package uz.gita.mobilbanking.viewmodel.main

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.response.CardInfoResponse

interface MainViewModel {
    val getTotalSumLiveData: LiveData<Double>
    val getFavoriteCardLiveData:LiveData<CardInfoResponse>
    val messageLiveData: LiveData<String>
    val errorLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<String>
    val progressLiveData: LiveData<Boolean>
    var ignoreTotalSum:Boolean
    var favoriteCardId:Int
}