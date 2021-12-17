package uz.gita.mobilbanking.ui.screenOnCreated.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenSplashBinding
import uz.gita.mobilbanking.ui.screenOnCreated.viewModel.SplashViewModel
import uz.gita.mobilbanking.utils.showToast

class SplashScreen : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)
    private val viewModel: SplashViewModel by viewModels()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lottie = binding.lottie
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.successPinLiveData.observe(this, successPinObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)

        lottie.animate().translationY(2000F).setDuration(500).startDelay = 2900
        Handler(Looper.getMainLooper()).postDelayed({
           viewModel.updateTokens()
        }, 4000)
    }
    private val errorObserver = Observer<String> {
        findNavController().navigate(SplashScreenDirections.actionSplashScreenToLoginScreen())
    }
    private val successPinObserver = Observer<Unit> {
        findNavController().navigate(SplashScreenDirections.actionSplashScreenToPinScreen())
    }
    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }
}