package uz.gita.mobilbanking.viewmodel.main.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.domain.usecase.main.MainUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.main.MainViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val mainUseCase: MainUseCase
) : MainViewModel, ViewModel() {
    override val getTotalSumLiveData = MediatorLiveData<Double>()
    override val getFavoriteCardLiveData = MediatorLiveData<CardInfoResponse>()
    override val messageLiveData = MediatorLiveData<String>()
    override val errorLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()
    override val logoutLiveData = MediatorLiveData<Unit>()

    override var ignoreTotalSum: Boolean
        get() = mainUseCase.ignoreTotalSum
        set(value) {
            mainUseCase.ignoreTotalSum = value
        }
    override var favoriteCardId: Int
        get() = mainUseCase.favoriteCardId
        set(value) {
            mainUseCase.favoriteCardId = value
        }

    init {
        getMains()
    }
    override fun getMains(){
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            getTotalSumLiveData.addSource(mainUseCase.getTotalSum()) {
                when (it) {
                    is MyResult.Success -> getTotalSumLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                    is MyResult.Logout -> logoutLiveData.value = Unit
                }
            }
            getFavoriteCardLiveData.addSource(mainUseCase.getFavoriteCard()) {
                progressLiveData.value = false
                var isFavorite = false
                when (it) {
                    is MyResult.Success -> {
                        when {
                            favoriteCardId == -1 -> {
                                if(it.data.isNotEmpty()){
                                getFavoriteCardLiveData.value = it.data[0]
                                isFavorite = true
                                return@addSource}
                            }
                            it.data.size == 1 -> {
                                getFavoriteCardLiveData.value = it.data[0]
                                favoriteCardId = it.data[0].id
                                isFavorite = true
                                return@addSource
                            }
                            else -> {
                                for (i in it.data) {
                                    if (i.id == favoriteCardId) {
                                        getFavoriteCardLiveData.value = i
                                        isFavorite = true
                                        return@addSource
                                    }
                                }
                            }
                        }
                        if (!isFavorite) {
                            if(it.data.isNotEmpty())
                            getFavoriteCardLiveData.value = it.data[0]
                        }
                    }
                    is MyResult.Message -> {
                        messageLiveData.value = it.data
                    }
                    is MyResult.Error -> {
                        errorLiveData.value = it.error.toString()
                    }
                    is MyResult.Logout -> logoutLiveData.value = Unit
                }
            }
        }
    }
}