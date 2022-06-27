package uz.gita.mobilbanking.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenOfflineBinding


@AndroidEntryPoint
class OfflineScreen : Fragment(R.layout.screen_offline) {
    private val binding by viewBinding(ScreenOfflineBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}