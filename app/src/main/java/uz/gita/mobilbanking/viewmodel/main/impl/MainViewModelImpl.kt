package uz.gita.mobilbanking.viewmodel.main.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.domain.usecase.main.MainUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.main.MainViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val mainUseCase: MainUseCase
) : MainViewModel, ViewModel() {
    override val setTotalSumLiveData = MediatorLiveData<Double>()
    override val messageLiveData = MediatorLiveData<String>()
    override val errorLiveData = MediatorLiveData<String>()
    override val notConnectionLiveData = MediatorLiveData<String>()
    override val progressLiveData = MediatorLiveData<Boolean>()
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
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            progressLiveData.value = true
            setTotalSumLiveData.addSource(mainUseCase.getTotalSum()) {
                progressLiveData.value = false
                when (it) {
                    is MyResult.Success -> setTotalSumLiveData.value = it.data!!
                    is MyResult.Message -> messageLiveData.value = it.data
                    is MyResult.Error -> errorLiveData.value = it.error.toString()
                }
            }
        }
    }
}