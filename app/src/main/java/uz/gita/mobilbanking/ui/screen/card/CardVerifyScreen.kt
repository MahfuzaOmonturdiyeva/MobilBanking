package uz.gita.mobilbanking.ui.screen.card

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.broadcast.ReceiveSms
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.databinding.ScreenAuthVerifyBinding
import uz.gita.mobilbanking.ui.dialog.CustomDialog
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.card.impl.CardVerifyViewModelImpl

@AndroidEntryPoint
class CardVerifyScreen : Fragment(R.layout.screen_auth_verify) {
    private val binding by viewBinding(ScreenAuthVerifyBinding::bind)
    private val viewModel: CardVerifyViewModelImpl by viewModels()
    private val navArgs: CardVerifyScreenArgs by navArgs<CardVerifyScreenArgs>()
    private val myReceiveSms: ReceiveSms by lazy { ReceiveSms() }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.successVerifyLiveData.observe(this, successVerifyObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.logoutLiveData.observe(this, logoutObserver)
        myReceiveSms.codeLiveData.observe(this, codeObserver)
        binding.progress.visibility = View.GONE
        onPerMissionState()
        binding.tVTimer.visibility = View.INVISIBLE
        binding.btnResend.visibility = View.INVISIBLE

        binding.btnVerify.setOnClickListener {
            onClickBtnVerify()
        }

        binding.mETEditCodeUser.textChanges().debounce(1000).onEach {
            binding.mETEditCodeUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorCode.text = ""
            it?.let { txt ->
                val code = txt.filter {
                    it.isDigit()
                }
                if (code.length == 6) {
                    onClickBtnVerify()
                }
            }
        }.launchIn(lifecycleScope)
    }
    private val logoutObserver= Observer<Unit> {
        findNavController().navigate(CardVerifyScreenDirections.actionGlobalLoginScreen())
    }

    private val successVerifyObserver = Observer<CardInfoResponse> {
       it?.let{
           if(navArgs.isFavorite) viewModel.favoriteCardId=it.id
       }
        val dialog = CustomDialog.Builder(requireContext())
            .setTitle("The card was successfully added", R.color.teal_500)
            .setDescription("Would you like to return to the main menu?")
            .setCancelBtn(R.color.gold_color) {
                findNavController().navigate(CardVerifyScreenDirections.actionCardVerifyScreenToAllCardsScreen())
            }
            .setOkBtn(R.color.teal_500) {
                findNavController().navigate(CardVerifyScreenDirections.actionGlobalMainScreen2())
            }
        dialog.build().show()
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

    private val codeObserver = Observer<String> {
        binding.mETEditCodeUser.setText(it)
        onClickBtnVerify()
    }

    private fun onClickBtnVerify() {
        val text = binding.mETEditCodeUser.text.toString()
        val code = text.filter {
            it.isDigit()
        }

        if (code.length == 6) {
            val data = AddCardVerifyRequest(
                pan = navArgs.pan,
                code = code
            )
            viewModel.addCardVerify(data)
        } else {
            if (code.isEmpty()) {
                binding.mETEditCodeUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorCode.text = "Enter the code"
            }
            if (code.length < 6) {
                binding.mETEditCodeUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorCode.text = "code should not be less than 6 digits"
            }
        }
    }


    /**
     * onkeylistner uchun
     */
    //    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
//        p2?.let {
//            if (it.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER) {
//                onClickBtnVerify()
//                return true
//            }
//        }
//        return false
//    }

    private fun onPerMissionState() {
        if (checkSelfPermission(requireContext(), android.Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            val request = arrayOf(android.Manifest.permission.RECEIVE_SMS)
            requestPermissions(request, 1000)
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED")
        requireActivity().registerReceiver(myReceiveSms, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(myReceiveSms)
    }
}

