package uz.gita.mobilbanking.ui.screen.auth

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.broadcast.ReceiveSms
import uz.gita.mobilbanking.data.request.NewPasswordRequest
import uz.gita.mobilbanking.databinding.ScreenAuthResetBinding
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.auth.impl.ResetViewModel1Impl
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ResetScreen : Fragment(R.layout.screen_auth_reset), View.OnKeyListener {
    private val binding by viewBinding(ScreenAuthResetBinding::bind)
    private val viewModel: ResetViewModel1Impl by viewModels()
    private var isSuccessReset = false
    private var phoneNumberUser = ""
    private var timer: CountDownTimer? = null
    private val myReceiveSms: ReceiveSms by lazy { ReceiveSms() }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.visibilityNewPasswordLiveData.observe(this, visibilityNewPasswordObserver)
        viewModel.openLoginLiveData.observe(this, openLoginObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        myReceiveSms.codeLiveData.observe(this, codeObserver)
        binding.progress.visibility = View.GONE

        binding.btnReset.setOnClickListener {
            if (!isSuccessReset) {
                onClickButtonReset()
                onPerMissionState()
            } else onClickButtonNewPassword()
        }
        binding.btnResend.setOnClickListener {
            onClickButtonReset()
        }

        binding.mETEditPhoneUser.doOnTextChanged { text, start, before, count ->
            binding.mETEditPhoneUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorPhone.text = ""
        }
        binding.eTEditPasswordUser.doOnTextChanged { text, start, before, count ->
            binding.eTEditPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorPassword.text = ""
        }
        binding.eTEditConfirmPasswordUser.doOnTextChanged { text, start, before, count ->
            binding.eTEditConfirmPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorConfirmPassword.text = ""
        }
        binding.mETEditCodeUser.doOnTextChanged { text, start, before, count ->
            binding.mETEditCodeUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorCode.text = ""
        }

        if (!isSuccessReset) {
            binding.mETEditPhoneUser.setOnKeyListener(this)
        } else binding.mETEditCodeUser.setOnKeyListener(this)
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val visibilityNewPasswordObserver = Observer<Unit> {
        isSuccessReset = true
        binding.mETEditPhoneUser.isClickable = false
        binding.lineNewPassword.visibility = View.VISIBLE
        binding.btnReset.text = "Next"
        setDownTimer()
    }

    private val openLoginObserver = Observer<Unit> {
        findNavController().navigate(ResetScreenDirections.actionResetScreenToLoginScreen())
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
        onClickButtonNewPassword()
    }

    private fun onClickButtonReset() {
        val text = binding.mETEditPhoneUser.text.toString()
        var phoneNumber = "+"
        if (text[0] == '+') {
            phoneNumber += text.filter {
                it.isDigit()
            }
        }
        if (phoneNumber.length == 13) {
            phoneNumberUser = phoneNumber
            viewModel.reset(phoneNumber)

//            binding.mETEditPhoneUser.isClickable = false
//            binding.lineNewPassword.visibility = View.VISIBLE
//            binding.btnReset.text = "Next"
//            setDownTimer()
        } else {
            if (phoneNumber.length == 4) {
                binding.mETEditPhoneUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorPhone.text = "Enter the number"
            }
            if (phoneNumber.length in 5..12) {
                binding.mETEditPhoneUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorPhone.text = "Phone number entered incorrectly"
            }
        }
    }

    private fun onClickButtonNewPassword() {
        val password = binding.eTEditPasswordUser.text.toString()
        val confirmPassword = binding.eTEditConfirmPasswordUser.text.toString()
        val text = binding.mETEditCodeUser.text.toString()
        val code = text.filter {
            it.isDigit()
        }

        if (password.length >= 6 && confirmPassword == password && code.length == 6) {
            val data = NewPasswordRequest(
                phoneNumberUser,
                password,
                code
            )
            viewModel.newPassword(data)
        } else {
            when {
                password.isEmpty() -> {
                    binding.eTEditPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVErrorPassword.text = "Enter the new password"
                }
                password.length < 6 -> {
                    binding.eTEditPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVErrorPassword.text = "Password must be at least 6 characters"
                }
                confirmPassword.isEmpty() -> {
                    binding.eTEditConfirmPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVErrorConfirmPassword.text = "Enter the confirm password"
                }
                password != confirmPassword -> {
                    binding.eTEditConfirmPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVErrorConfirmPassword.text =
                        "Confirmation password is not compatible with the password"
                }
            }
            if (code.isEmpty()) {
                binding.mETEditCodeUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorCode.text = "Enter the code"
            } else if (code.length < 6) {
                binding.mETEditCodeUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorCode.text = "code should not be less than 6 digits"
            }
        }
    }

    private fun setDownTimer() {
        val duration = TimeUnit.MINUTES.toMillis(1)
        binding.tVTimer.visibility = View.VISIBLE

        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(p0: Long) {
                val timer = String.format(
                    Locale.ENGLISH, "%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(p0),
                    TimeUnit.MILLISECONDS.toMinutes(p0),
                    TimeUnit.MILLISECONDS.toSeconds(p0) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(
                            p0
                        )
                    )
                )
                binding.tVTimer.setText(timer)
                binding.btnResend.isEnabled = false
                binding.btnResend.text = "The sms was sent to a $phoneNumberUser number"
            }

            override fun onFinish() {
                binding.tVTimer.visibility = View.GONE
                binding.btnResend.isEnabled = true
                binding.btnResend.text = "send sms again"
            }
        }.start()
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        p2?.let {
            if (it.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER) {
                if (!isSuccessReset) {
                    onClickButtonReset()
                } else onClickButtonNewPassword()
                return true
            }
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }

    private fun onPerMissionState() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECEIVE_SMS)
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