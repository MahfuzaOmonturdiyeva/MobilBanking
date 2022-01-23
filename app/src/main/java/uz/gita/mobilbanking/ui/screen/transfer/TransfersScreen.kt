package uz.gita.mobilbanking.ui.screen.transfer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.databinding.ScreenTransfersBinding
import uz.gita.mobilbanking.ui.dialog.CustomDialog
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.transfers.impl.TransfersViewModelImpl

@AndroidEntryPoint
class TransfersScreen : Fragment(R.layout.screen_transfers) {
    private val binding by viewBinding(ScreenTransfersBinding::bind)
    private val viewModel: TransfersViewModelImpl by activityViewModels<TransfersViewModelImpl>()
    private var senderId = -1

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getSenderCardId()
        viewModel.getSenderCard()
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.senderIdLiveData.observe(
            viewLifecycleOwner){
            senderId=it
        }
        viewModel.receiverPanWithMyCards.observe(viewLifecycleOwner){
            binding.metEditPanCard.setText(it)
        }
        viewModel.senderCardLiveData.observe(viewLifecycleOwner){
            it?.let {
                binding.containerMoneyTransferCard.imgStar.visibility=View.GONE
                binding.containerMoneyTransferCard.tvBalanceCard.text=it.balance.toString()
                binding.containerMoneyTransferCard.tvCardNumber.text=it.pan.substring(12, 16)
                binding.containerMoneyTransferCard.tvNameCard.text=it.cardName
            }
        }
        viewModel.successSendMoneyLiveData.observe(this) {
            showToast("Pul o'tkazildi!")
            findNavController().navigate(TransfersScreenDirections.actionTransfersScreen2ToMainScreen2())
        }
        binding.lineReceiverMyCards.setOnClickListener {
            findNavController().navigate(TransfersScreenDirections.actionTransfersScreen2ToAllReceiverCardsScreen())
        }

        binding.containerMoneyTransferCard.clContainerCards.setOnClickListener{
            findNavController().navigate(TransfersScreenDirections.actionTransfersScreen2ToAllSenderCardsScreen())
        }

        binding.imgBtnClose.setOnClickListener {
            closeScreen()
        }
        binding.btnTransfer.setOnClickListener {
            transferMoney()
        }
        binding.metEditPanCard.doOnTextChanged { text, start, before, count ->
            binding.metEditPanCard.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tvErrorPanCard.text = ""
        }
        binding.metEditTransferAmount.doOnTextChanged { text, start, before, count ->
            binding.metEditTransferAmount.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tvErrorTransferAmount.text = ""
        }
    }

    private fun transferMoney(){
        var pan=binding.metEditPanCard.text.toString()
        pan=pan.filter {
            it!=' '
        }
        var sum=binding.metEditTransferAmount.text.toString()
        sum=sum.filter {
            it!=' '
        }
        if(pan.length==16 && sum.isNotEmpty()){
            val sumWithFee=sum.toInt()+sum.toInt()*0.01
            val dialog = CustomDialog.Builder(requireContext())
                .setTitle("Do you really want to transfer money?", R.color.red_400)
                .setDescription(binding.metEditPanCard.text.toString()+"\n"+"sum: "+sumWithFee.toString())
                .setCancelBtn(R.color.teal_500)
                .setOkBtn(R.color.red_400) {
                    viewModel.sendMoney(SendMoneyRequest(sum.toInt(),pan,senderId))
                }
            dialog.build().show()
        }else{
            if (pan.isEmpty()) {
                binding.metEditPanCard.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tvErrorPanCard.text = "Enter the card number"
            } else if (pan.length<16) {
                binding.metEditPanCard.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tvErrorPanCard.text = "Card number must be  16 letter"
            }
            if (sum.isEmpty()) {
                binding.metEditTransferAmount.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tvErrorTransferAmount.text = "Enter the sum"
            }
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