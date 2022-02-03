package uz.gita.mobilbanking.ui.screen.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenSettingsSupportBinding

@AndroidEntryPoint
class SupportScreen : Fragment(R.layout.screen_settings_support) {
    private val binding by viewBinding(ScreenSettingsSupportBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.imgBtnClose.setOnClickListener {
            findNavController().navigate(SupportScreenDirections.actionSupportScreen2ToSettingsScreen())
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(SupportScreenDirections.actionSupportScreen2ToSettingsScreen())
            }
        })

        binding.contactUs.setOnClickListener {
            val phone = Uri.parse("tel:+998975351717")
            requireActivity().startActivity(Intent(Intent.ACTION_DIAL, phone))
        }

        binding.writeUs.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("mailto:m.talabaomonturdiyeva@gmail.com"))
            startActivity(intent)
        }
    }
}