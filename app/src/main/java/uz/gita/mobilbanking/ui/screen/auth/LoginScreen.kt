package uz.gita.mobilbanking.ui.screen.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.request.LoginRequest
import uz.gita.mobilbanking.databinding.ScreenAuthLoginBinding
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.auth.impl.LoginViewModel1Impl

@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_auth_login), View.OnKeyListener {
    private val binding by viewBinding(ScreenAuthLoginBinding::bind)
    private val viewModel: LoginViewModel1Impl by viewModels()
    private var phone = ""

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openVerifyLiveData.observe(this, openVerifyObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)

       
        binding.btnForgotPassword.setOnClickListener {
            findNavController().navigate(LoginScreenDirections.actionLoginScreenToResetScreen())
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(LoginScreenDirections.actionLoginScreenToRegisterScreen())
        }

        binding.btnLogin.setOnClickListener {
            onClickLogin()
        }

        binding.eTEditPasswordUser.doOnTextChanged { text, start, before, count ->
            binding.eTEditPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorPassword.text = ""
        }

        binding.mETEditPhoneUser.doOnTextChanged { text, start, before, count ->
            binding.mETEditPhoneUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorPhone.text = ""
        }

        binding.eTEditPasswordUser.setOnKeyListener(this)
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val openVerifyObserver = Observer<Unit> {
        findNavController().navigate(
            LoginScreenDirections.actionLoginScreenToVerifyScreen(
                phone,
                binding.eTEditPasswordUser.text.toString()
            )
        )
    }
    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }
    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progress.root.visibility = View.VISIBLE
        } else {
            binding.progress.root.visibility = View.GONE
        }
    }

    private fun onClickLogin() {
        val text = binding.mETEditPhoneUser.text.toString()
        var phoneNumber = "+"
        val password = binding.eTEditPasswordUser.text.toString()
        if (text[0] == '+') {
            phoneNumber += text.filter {
                it.isDigit()
            }
        }

        if (phoneNumber.length == 13 && binding.eTEditPasswordUser.text.toString().length >= 6) {
            phone = phoneNumber
            val data = LoginRequest(phoneNumber, password)
            viewModel.login(data)
        } else {
            if (phoneNumber.length == 4) {
                binding.mETEditPhoneUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorPhone.text = "Enter the number"
            }
            if (phoneNumber.length in 5..12) {
                binding.mETEditPhoneUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorPhone.text = "Phone number entered incorrectly"
            }
            if (password.isEmpty()) {
                binding.eTEditPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorPassword.text = "Enter the password"
            } else if (password.length < 6) {
                binding.eTEditPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorPassword.text = "Password must be at least 6 characters"
            }
        }
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        p2?.let {
            if (it.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER) {
                onClickLogin()
                return true
            }
        }
        return false
    }
}