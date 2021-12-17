package uz.gita.mobilbanking.ui.screenMain.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mobilbanking.R
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import uz.gita.mobilbanking.data.response.CardDataResponse
import uz.gita.mobilbanking.databinding.ScreenMainBinding
import uz.gita.mobilbanking.ui.screenMain.adapter.CardAdapter
import uz.gita.mobilbanking.ui.screenMain.adapter.CardAdapter1
import uz.gita.mobilbanking.ui.screenMain.viewModel.MainViewModel
import uz.gita.mobilbanking.utils.showToast

class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progress.visibility = View.GONE
        val list= arrayListOf<CardDataResponse>(CardDataResponse("8600111122224441", "10/23", "Card1"),
            CardDataResponse("8600111122224442", "10/23", "Card2"),
            CardDataResponse("8600111122224443", "10/23", "Card3"),
            CardDataResponse("8600111122224444", "10/23", "Card4")
            )

        val adapter= CardAdapter1(requireContext(), R.layout.item_stack_view_card, list)

        binding.stackVContainerCards.adapter=adapter
        binding.imgBtnSettings.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreenToSettingsScreen())
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
            binding.progress.visibility = View.VISIBLE
        } else binding.progress.visibility = View.GONE
    }
}