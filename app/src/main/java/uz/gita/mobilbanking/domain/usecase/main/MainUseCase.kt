package uz.gita.mobilbanking.domain.usecase.main

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.response.CardInfoResponse

interface MainUseCase {
    var ignoreTotalSum:Boolean
    var favoriteCardId:Int
//    fun getFavoriteCard():LiveData<MyResult<CardInfoResponse>>
    fun getTotalSum(): LiveData<MyResult<Double>>
}