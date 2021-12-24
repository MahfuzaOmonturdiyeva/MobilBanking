package uz.gita.mobilbanking.ui.screenMainIshlatilmagani.screen

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
import uz.gita.mobilbanking.databinding.ScreenMainClickVersionCardBinding
import uz.gita.mobilbanking.ui.screenMainIshlatilmagani.adapter.CardAdapterForClickVersion
import uz.gita.mobilbanking.ui.screenMainIshlatilmagani.viewModel.MainClickVersionCardViewModel
import uz.gita.mobilbanking.utils.showToast


class MainClickVersionCardScreen : Fragment(R.layout.screen_main_click_version_card) {
    private val binding by viewBinding(ScreenMainClickVersionCardBinding::bind)
    private val viewModel: MainClickVersionCardViewModel by viewModels()
    private var backClickedTime: Long = -1

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progress.visibility = View.GONE
        val list = arrayListOf<CardDataResponse>(
            CardDataResponse("8600111122224441", "10/23", "Card1"),
            CardDataResponse("8600111122224442", "10/23", "Card2"),
            CardDataResponse("8600111122224443", "10/23", "Card3"),
            CardDataResponse("8600111122224444", "10/23", "Card4")
        )

        val adapter =
            CardAdapterForClickVersion(requireContext(), R.layout.item_stack_view_card, list)

        binding.stackVContainerCards.adapter = adapter
        binding.imgBtnSettings.setOnClickListener {
            findNavController().navigate(MainClickVersionCardScreenDirections.actionMainScreenToSettingsScreen())
        }
//        viewModel.errorLiveData.observe(this, errorObserver)
//        viewModel.successLogoutLiveData.observe(this, successLogoutObserver)
//        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
//        viewModel.progressLiveData.observe(this, progressObserver)

    }


//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
//            true // default to enabled
//        ) {
//            override fun handleOnBackPressed() {
//                Timber.d("back clicked")
//                Log.d("RRR", "bck")
//                if (backClickedTime != -1L) {
//                    if ((System.currentTimeMillis() - backClickedTime) / 1000 < 2)
//                        findNavController().popBackStack()
//                    else backClickedTime = -1L
//                } else {
//                    backClickedTime = System.currentTimeMillis()
//                }
//            }
//        }
//        activity?.onBackPressedDispatcher?.addCallback(this, callback)
//    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val successLogoutObserver = Observer<Unit> {
        findNavController().navigate(MainClickVersionCardScreenDirections.actionMainScreenToSettingsScreen())
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