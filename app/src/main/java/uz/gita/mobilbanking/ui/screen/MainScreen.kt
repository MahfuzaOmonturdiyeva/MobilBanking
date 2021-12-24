package uz.gita.mobilbanking.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenMainBinding

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_pin) {
    private val binding by viewBinding(ScreenMainBinding::bind)

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progress.visibility = View.GONE

        binding.imgBtnSettings.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreen2ToSettingsScreen())
        }
    }

//    private val errorObserver = Observer<String> {
//        showToast(it)
//    }
//    private val successLogoutObserver = Observer<Unit> {
//        findNavController().navigate(MainScreenDirections.actionMainScreen2ToSettingsScreen())
//    }
//    private val notConnectionObserver = Observer<String> {
//        showToast(it)
//    }
//    private val progressObserver = Observer<Boolean> {
//        if (it) {
//            binding.progress.visibility = View.VISIBLE
//        } else binding.progress.visibility = View.GONE
//    }
}