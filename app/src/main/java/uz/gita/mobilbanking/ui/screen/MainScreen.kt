package uz.gita.mobilbanking.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.databinding.ScreenMainBinding
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.main.impl.MainViewModelImpl

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModelImpl by viewModels()
    private var totalSum = 0.0

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progress.visibility = View.GONE
        viewModel.getTotalSumLiveData.observe(this, getTotalSumObserver)
        viewModel.getFavoriteCardLiveData.observe(this, getFavoriteCardObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)

        binding.imgBtnSettings.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreen2ToSettingsScreen())
        }
        binding.imgBtnEyeIsShowTotalSum.setOnClickListener {
            if (!viewModel.ignoreTotalSum) {
                binding.imgBtnEyeIsShowTotalSum.setImageResource(R.drawable.ic_eye)
                binding.tvTotalSum.text = "View balance"
                viewModel.ignoreTotalSum = true
            } else {
                viewModel.ignoreTotalSum = false
                binding.tvTotalSum.text = totalSum.toString()
                binding.imgBtnEyeIsShowTotalSum.setImageResource(R.drawable.ic_eye_teal_500)
            }
        }
        binding.lineReports.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreen2ToTransfersHistoryScreen2())
        }
        binding.lineTransfers.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreen2ToTransfersScreen2())
        }
        binding.cLineFavoriteCard.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreen2ToAllCardsScreen())
        }
        binding.btnAddCard.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreen2ToAddCardScreen())
        }
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val messageObserver = Observer<String> {
        showToast(it)
    }

    private val getTotalSumObserver = Observer<Double> {
        if (viewModel.ignoreTotalSum) {
            binding.imgBtnEyeIsShowTotalSum.setImageResource(R.drawable.ic_eye)
            binding.tvTotalSum.text = ""
        } else {
            binding.imgBtnEyeIsShowTotalSum.setImageResource(R.drawable.ic_eye_teal_500)
            binding.tvTotalSum.text = it.toString()
            totalSum = it
        }
    }
    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }
    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progress.visibility = View.VISIBLE
        } else binding.progress.visibility = View.GONE
    }
    private val getFavoriteCardObserver = Observer<CardInfoResponse> {
        binding.tvNameCard.text = it.cardName
        binding.tvBalanceCard.text = it.balance.toString()
        binding.tvCardNumber.text = it.pan.substring(12, 16)
    }
}