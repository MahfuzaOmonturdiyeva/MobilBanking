package uz.gita.mobilbanking.viewmodel.onCreated.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.domain.repository.impl.InvalidTokenException
import uz.gita.mobilbanking.domain.usecase.onCreated.SplashUseCase
import uz.gita.mobilbanking.utils.isConnected
import uz.gita.mobilbanking.viewmodel.onCreated.SplashViewModel1
import javax.inject.Inject

@HiltViewModel
class SplashViewModel1Impl @Inject constructor(
    private val splashUseCase: SplashUseCase
) : SplashViewModel1, ViewModel() {
    override val openPinLiveData = MediatorLiveData<Unit>()
    override val openLoginLiveData = MediatorLiveData<Unit>()
    override val notConnectionLiveData = MediatorLiveData<String>()

    init {
        viewModelScope.launch {
            if (!isConnected()) {
                notConnectionLiveData.value = "Internet not connection"
            }
            openPinLiveData.addSource(splashUseCase.check()) {
                when (it) {
                    is MyResult.Success -> openPinLiveData.value = Unit
                    is MyResult.Message -> openLoginLiveData.value = Unit
                    is MyResult.Error -> {
                        if (it.error is InvalidTokenException) {
                            openLoginLiveData.value = Unit
                        }
                    }
                }
            }
        }
    }
}