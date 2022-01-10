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
import uz.gita.mobilbanking.databinding.ScreenMainBinding
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.main.impl.MainViewModelImpl

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModelImpl by viewModels()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progress.visibility = View.GONE
        viewModel.setTotalSumLiveData.observe(this, setTotalSumObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)

        if(viewModel.favoriteCardId==-1){
            binding.cLineFavoriteCard.visibility=View.GONE
        }else{
            binding.cLineFavoriteCard.visibility=View.VISIBLE
        }

        binding.imgBtnSettings.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreen2ToSettingsScreen())
        }
        binding.imgBtnEyeIsShowTotalSum.setOnClickListener {
            if(!viewModel.ignoreTotalSum){
                binding.imgBtnEyeIsShowTotalSum.setImageResource(R.drawable.ic_eye)
                binding.tvTotalSum.text=""
                viewModel.ignoreTotalSum=false
            }else{
                viewModel.ignoreTotalSum=true
                binding.imgBtnEyeIsShowTotalSum.setImageResource(R.drawable.ic_eye_teal_500)
            }
        }
        binding.lineReports.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreen2ToTransfersHistoryScreen2())
        }
        binding.lineTransfers.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreen2ToTransfersScreen2())
        }
        binding.cLineFavoriteCard.setOnClickListener{

        }
    }
    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val messageObserver= Observer<String> {
        showToast(it)
    }

    private val setTotalSumObserver = Observer<Double> {
        if(viewModel.ignoreTotalSum){
            binding.imgBtnEyeIsShowTotalSum.setImageResource(R.drawable.ic_eye)
            binding.tvTotalSum.text=""
        }else{
            binding.imgBtnEyeIsShowTotalSum.setImageResource(R.drawable.ic_eye_teal_500)
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
}