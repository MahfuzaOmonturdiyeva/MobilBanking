package uz.gita.mobilbanking.ui.screen.transfer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.mobilbanking.databinding.ScreenTransfersHistoryBinding
import uz.gita.mobilbanking.ui.adapter.TransfersHistoryAdapter
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.transfers.impl.TransfersHistoryViewModelImpl


@AndroidEntryPoint
class TransfersHistoryScreen : Fragment(R.layout.screen_transfers_history) {
    private val binding by viewBinding(ScreenTransfersHistoryBinding::bind)
    private val viewModel: TransfersHistoryViewModelImpl by viewModels()
    private val adapter: TransfersHistoryAdapter by lazy { TransfersHistoryAdapter() }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.containerItemsFragmentHistory.adapter = adapter
        binding.containerItemsFragmentHistory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewModel.getHistory()
        viewModel.setHistoryLiveData.observe(this) {
            lifecycleScope.launchWhenResumed {
                adapter.submitData(it)
            }
        }
        viewModel.setOutComesLiveData.observe(this) {
            lifecycleScope.launchWhenResumed {
                adapter.submitData(PagingData.empty())
                adapter.submitData(it)
            }
        }
        viewModel.setInComesLiveData.observe(this) {
            lifecycleScope.launchWhenResumed {
                adapter.submitData(PagingData.empty())
                adapter.submitData(it)
            }
        }
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)

        binding.imgBtnClose.setOnClickListener {
            closeScreen()
        }
        binding.bottomNavigationHistoryScreen.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_history -> {
                    viewModel.getHistory()
                }
                R.id.action_income -> {
                    viewModel.getIncomes()
                }
                R.id.action_outcome -> {
                    viewModel.getOutcomes()
                }
            }
            true
        }
    }

    private val messageObserver = Observer<String> {
        showToast(it)
    }
    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }
    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progress.visibility = View.VISIBLE
        } else binding.progress.visibility = View.GONE
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