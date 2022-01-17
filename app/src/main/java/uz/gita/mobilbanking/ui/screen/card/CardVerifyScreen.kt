package uz.gita.mobilbanking.ui.screen.card

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.broadcast.ReceiveSms
import uz.gita.mobilbanking.data.request.LoginRequest
import uz.gita.mobilbanking.data.request.VerifyRequest
import uz.gita.mobilbanking.databinding.ScreenAuthVerifyBinding
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.auth.impl.VerifyViewModel1Impl
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CardVerifyScreen : Fragment(R.layout.screen_auth_verify) {
    private val binding by viewBinding(ScreenAuthVerifyBinding::bind)
    private val viewModel: VerifyViewModel1Impl by viewModels()
    private val navArgs: VerifyScreenArgs by navArgs<VerifyScreenArgs>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private var timer: CountDownTimer? = null
    private val myReceiveSms: ReceiveSms by lazy { ReceiveSms() }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.openNewPinLiveData.observe(this, openNewPinObserver)
        viewModel.successResendLiveData.observe(this, successResendObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        myReceiveSms.codeLiveData.observe(this, codeObserver)
        binding.progress.visibility = View.GONE
        setDownTimer()
        onPerMissionState()

        binding.btnVerify.setOnClickListener {
            onClickBtnVerify()
        }

        binding.btnResend.setOnClickListener {
            viewModel.resend(LoginRequest(navArgs.phone, navArgs.password))
        }

        binding.mETEditCodeUser.doOnTextChanged { text, start, before, count ->
            binding.mETEditCodeUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorCode.text = ""
        }

//        binding.mETEditCodeUser.setOnKeyListener(this)
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val openNewPinObserver = Observer<Unit> {
        lifecycleScope.launchWhenResumed {
            navController.navigate(VerifyScreenDirections.actionVerifyScreenToNewPinScreen())
        }

    }
    private val successResendObserver = Observer<Unit> {
        setDownTimer()
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
        timer?.cancel()
        onClickBtnVerify()
    }

    private fun onClickBtnVerify() {
        val text = binding.mETEditCodeUser.text.toString()
        val code = text.filter {
            it.isDigit()
        }

        if (code.length == 6) {
            val data = VerifyRequest(
                phone = navArgs.phone,
                code = code
            )
            viewModel.verify(data)
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

    private fun setDownTimer() {
        val duration = TimeUnit.MINUTES.toMillis(1)
        binding.tVTimer.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {

            timer = object : CountDownTimer(duration, 1000) {
                override fun onTick(p0: Long) {
                    val timer = String.format(
                        Locale.ENGLISH, "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(p0),
                        TimeUnit.MILLISECONDS.toSeconds(p0)
                    )
                    binding.tVTimer.setText(timer)
                    binding.btnResend.isEnabled = false
                    binding.btnResend.text = "The sms was sent to a ${navArgs.phone} number"
                }

                override fun onFinish() {
                    binding.tVTimer.visibility = View.GONE
                    binding.btnResend.isEnabled = true
                    binding.btnResend.text = "send sms again"
                }
            }.start()
        }
    }

    /**
     * sms ni olish uchun
     */
//    private fun onDeliveredMessage(): String {
//        val intent = Intent()
//        val myBundle = intent.extras;
//        var kod = ""
//
//        if (myBundle != null) {
//            val pdus = myBundle.get("pdus") as Array<*>
//            val messages = ArrayList<SmsMessage>()
//
//            for (i in pdus.indices) {
//                val number = messages[i].displayOriginatingAddress;
//                Toast.makeText(requireContext(),"number"+ number, Toast.LENGTH_SHORT).show()
//                val body = messages[i].messageBody;
//                val message = body.split(":")
//                kod = message[1].substring(1, message[1].length)
//            }
//        }
//        return kod
//    }

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
    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == 1000) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                showToast("permission olindi")
//            } else {
//                showToast("permission olinmadi")
//            }
//        }
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

