package uz.gita.mobilbanking.ui.screen.transfer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenTransfersBinding

@AndroidEntryPoint
class TransfersScreen : Fragment(R.layout.screen_transfers) {
    private val binding by viewBinding(ScreenTransfersBinding::bind)

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
    private fun closeScreen() {
        val count: Int = requireActivity().supportFragmentManager.backStackEntryCount
        if (count == 0) {
            requireActivity().onBackPressed()
        } else {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}