package uz.gita.mobilbanking.ui.screen.onCreated

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenSplashBinding
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.onCreated.impl.SplashViewModel1Impl

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)
    private val viewModel: SplashViewModel1Impl by viewModels()
    private lateinit var lottie: LottieAnimationView

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lottie = binding.lottie
        viewModel.openLoginLiveData.observe(this, openLoginObserver)
        viewModel.openPinLiveData.observe(this, openPinObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)

        lottie.animate().duration = 500
    }

    private val openPinObserver = Observer<Unit> {
        findNavController().navigate(SplashScreenDirections.actionSplashScreenToPinScreen())
    }
    private val openLoginObserver = Observer<Unit> {
        lottie.cancelAnimation()
        findNavController().navigate(SplashScreenDirections.actionSplashScreenToPinScreen())
    }
    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }
}