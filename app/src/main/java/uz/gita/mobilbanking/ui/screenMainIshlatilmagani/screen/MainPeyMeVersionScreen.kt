package uz.gita.mobilbanking.ui.screenMainIshlatilmagani.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenMainPeymeVersionBinding
import uz.gita.mobilbanking.ui.screenMainIshlatilmagani.viewModel.MainPeymeVersionViewModel
import uz.gita.mobilbanking.utils.showToast

class MainPeyMeVersionScreen : Fragment(R.layout.screen_main_peyme_version) {
    private val binding by viewBinding(ScreenMainPeymeVersionBinding::bind)
    private val viewModel: MainPeymeVersionViewModel by viewModels()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.circularProgressBar.apply {
            progressMax = 100f
            setProgressWithAnimation(50f, 1000)
            progressBarWidth = 5f
            backgroundProgressBarWidth = 2f
            progressBarColor = R.color.teal_100
        }

        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.successLogoutLiveData.observe(this, successLogoutObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val successLogoutObserver = Observer<Unit> {
//        findNavController().navigate(MainScreenDirections.actionMainScreenToLoginScreen())
    }
    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }
    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.circularProgressBar.visibility = View.VISIBLE
        } else binding.circularProgressBar.visibility = View.GONE
    }
}