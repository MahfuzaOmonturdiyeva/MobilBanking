package uz.gita.mobilbanking.ui.screen.transfer

import android.R.attr
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.request.FeeRequest
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.databinding.ScreenTransfersBinding
import uz.gita.mobilbanking.ui.dialog.CustomDialog
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.transfers.impl.TransfersViewModelImpl
import io.card.payment.CardIOActivity
import android.R.attr.data

import io.card.payment.CreditCard


@AndroidEntryPoint
class TransfersScreen : Fragment(R.layout.screen_transfers) {
    private val binding by viewBinding(ScreenTransfersBinding::bind)
    private val viewModel: TransfersViewModelImpl by activityViewModels<TransfersViewModelImpl>()
    private var senderId = -1
    private var isNotEnoughMoney = true
    private val MY_SCAN_REQUEST_CODE = 111

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getSenderCardId()
        viewModel.getSenderCard()
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.notFoundOwnerCardLiveData.observe(viewLifecycleOwner) {
            binding.tvErrorPanCard.setTextColor(resources.getColor(R.color.red_400))
            binding.tvErrorPanCard.text = it
        }
        viewModel.amountLiveData.observe(viewLifecycleOwner) {
            binding.metEditTransferAmount.setText(it)
        }

        viewModel.successFeeLiveData.observe(viewLifecycleOwner) {
            binding.tvErrorTransferAmount.text = it.toString()
        }
        viewModel.logoutLiveData.observe(this) {
            findNavController().navigate(TransfersHistoryScreenDirections.actionGlobalLoginScreen())
        }
        viewModel.notEnoughMoneyLivedata.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvErrorTransferAmount.text = it
                isNotEnoughMoney = false
            }
        }
        viewModel.ownerCardLiveData.observe(viewLifecycleOwner) {
            binding.tvErrorPanCard.setTextColor(resources.getColor(R.color.teal_300))
            binding.tvErrorPanCard.text = it
        }
        viewModel.senderIdLiveData.observe(
            viewLifecycleOwner
        ) {
            senderId = it
        }
        viewModel.receiverPanWithMyCards.observe(viewLifecycleOwner) {
            binding.metEditPanCard.setText(it)
        }
        viewModel.senderCardLiveData.observe(viewLifecycleOwner) {
            it?.let {
                binding.containerMoneyTransferCard.imgStar.visibility = View.GONE
                binding.containerMoneyTransferCard.tvBalanceCard.text = it.balance.toString()
                binding.containerMoneyTransferCard.tvCardNumber.text = it.pan.substring(12, 16)
                binding.containerMoneyTransferCard.tvNameCard.text = it.cardName
            }
        }
        viewModel.successSendMoneyLiveData.observe(this) {
            showToast("The money was transferred!")
            findNavController().navigate(TransfersScreenDirections.actionGlobalMainScreen2())
        }
        binding.lineReceiverMyCards.setOnClickListener {
            findNavController().navigate(TransfersScreenDirections.actionTransfersScreen2ToAllReceiverCardsScreen())
        }

        binding.containerMoneyTransferCard.clContainerCards.setOnClickListener {
            findNavController().navigate(TransfersScreenDirections.actionTransfersScreen2ToAllSenderCardsScreen())
        }

        binding.imgBtnClose.setOnClickListener {
            closeScreen()
        }
        binding.btnTransfer.setOnClickListener {
            transferMoney()
        }
        binding.metEditPanCard.textChanges().debounce(500).onEach {
            binding.metEditPanCard.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tvErrorPanCard.text = ""
            val pan = getFilterPan()
            if (pan.length == 16) {
                viewModel.getCardOwnerByPan(pan)
                viewModel.setSaverPan(pan)
            }
        }.launchIn(lifecycleScope)
        binding.metEditTransferAmount.textChanges().debounce(500).onEach {
            binding.metEditTransferAmount.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tvErrorTransferAmount.text = ""
            val amount = it.toString().filter { it ->
                it.isDigit()
            }
            if (senderId != -1 && amount.isNotEmpty()) {
                viewModel.fee(FeeRequest(senderId, amount.toDouble()))
                viewModel.setAmount(it.toString())
            }
        }.launchIn(lifecycleScope)

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun getFilterPan(): String {
        var pan = binding.metEditPanCard.text.toString()
        pan = pan.filter {
            it != ' '
        }
        return pan
    }

    private fun transferMoney() {
        val pan = getFilterPan()
        var sum = binding.metEditTransferAmount.text.toString()
        sum = sum.filter {
            it != ' '
        }
        if (pan.length == 16 && sum.isNotEmpty() && isNotEnoughMoney) {
            val sumWithFee = sum.toInt() + sum.toInt() * 0.01
            val dialog = CustomDialog.Builder(requireContext())
                .setTitle("Do you really want to transfer money?", R.color.red_400)
                .setDescription(binding.metEditPanCard.text.toString() + "\n" + "sum: " + sumWithFee.toString())
                .setCancelBtn(R.color.teal_500)
                .setOkBtn(R.color.red_400) {
                    viewModel.sendMoney(SendMoneyRequest(sum.toInt(), pan, senderId))
                }
            dialog.build().show()
        } else {
            if (pan.isEmpty()) {
                binding.metEditPanCard.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tvErrorPanCard.text = "Enter the card number"
            } else if (pan.length < 16) {
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
        findNavController().navigate(TransfersScreenDirections.actionGlobalMainScreen2())
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            closeScreen()
        }
    }

    private fun cardScan(v: View) {
        val scanIntent = Intent(requireActivity(), CardIOActivity::class.java)

        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true) // default: false

        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false) // default: false

        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false) // default: false

        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE)
    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode === MY_SCAN_REQUEST_CODE) {
//            var resultDisplayStr: String
//            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
//                val scanResult: CreditCard? =
//                    data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)
//                resultDisplayStr = "Card Number: ${scanResult?.redactedCardNumber} \n"
//
//                if (scanResult?.isExpiryValid == true) {
//                    resultDisplayStr += "Expiration Date: ${scanResult.expiryMonth}/${scanResult.expiryYear}\n"
//                }
//                if (scanResult?.cvv != null) {
//                    resultDisplayStr += "CVV has " + scanResult?.cvv.length() + " digits.\n";
//                }
//                if (scanResult?.postalCode != null) {
//                    resultDisplayStr += "Postal Code: ${scanResult.postalCode}"
//
//                    """.trimIndent()
//                }
//            } else {
//                resultDisplayStr = "Scan was canceled."
//            }
//            // do something with resultDisplayStr, maybe display it in a textView
//            // resultTextView.setText(resultDisplayStr);
//        }
//        // else handle other activity results
//    }
}