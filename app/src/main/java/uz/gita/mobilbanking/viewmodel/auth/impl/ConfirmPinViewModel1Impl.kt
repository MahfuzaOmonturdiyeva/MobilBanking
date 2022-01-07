package uz.gita.mobilbanking.viewmodel.auth.impl

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.mobilbanking.domain.usecase.auth.ConFirmPinUseCase
import uz.gita.mobilbanking.domain.usecase.auth.impl.ConFirmPinUseCaseImpl
import uz.gita.mobilbanking.viewmodel.auth.ConfirmPinViewModel1
import javax.inject.Inject

@HiltViewModel
class ConfirmPinViewModel1Impl @Inject constructor(
    private val usecase:ConFirmPinUseCase
): ConfirmPinViewModel1, ViewModel() {
    override fun setPinLocal(pin: String)=usecase.setPinLocal(pin)
}