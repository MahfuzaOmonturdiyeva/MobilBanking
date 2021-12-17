package uz.gita.mobilbanking.ui.screenSetting.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenSettingsSecurityBinding

class SecurityScreen : Fragment(R.layout.screen_settings_security) {
    private val binding by viewBinding(ScreenSettingsSecurityBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imgBtnClose.setOnClickListener {
            val count: Int = requireActivity().supportFragmentManager.backStackEntryCount
            if (count == 0) {
                requireActivity().onBackPressed()
            } else {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}