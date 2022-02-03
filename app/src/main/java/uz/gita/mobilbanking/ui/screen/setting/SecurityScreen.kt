package uz.gita.mobilbanking.ui.screen.setting

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenSettingsSecurityBinding

@AndroidEntryPoint
class SecurityScreen : Fragment(R.layout.screen_settings_security) {
    private val binding by viewBinding(ScreenSettingsSecurityBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imgBtnClose.setOnClickListener {
            findNavController().navigate(SecurityScreenDirections.actionSecurityScreenToSettingsScreen())
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(SecurityScreenDirections.actionSecurityScreenToSettingsScreen())
            }
        })
    }
}