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
import uz.gita.mobilbanking.databinding.ScreenTransfersHistoryBinding

@AndroidEntryPoint
class TransfersHistoryScreen : Fragment(R.layout.screen_transfers_history) {
    private val binding by viewBinding(ScreenTransfersHistoryBinding::bind)

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}