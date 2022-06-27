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
import uz.gita.mobilbanking.data.request.RegisterRequest
import uz.gita.mobilbanking.databinding.ScreenAuthRegisterBinding
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.auth.impl.RegisterViewModel1Impl

@AndroidEntryPoint
class RegisterScreen : Fragment(R.layout.screen_auth_register), View.OnKeyListener {
    private val binding by viewBinding(ScreenAuthRegisterBinding::bind)
    private val viewModel: RegisterViewModel1Impl by viewModels()
    private var phone = ""

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.openVerifyLiveData.observe(this, openVerifyObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        binding.progress.root.visibility = View.GONE

        binding.btnRegister.setOnClickListener {
            onClickBtnRegistration()
        }

        binding.eTEditFirstnameUser.doOnTextChanged { text, start, before, count ->
            binding.eTEditFirstnameUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorFirstname.text = ""
        }
        binding.eTEditLastnameUser.doOnTextChanged { text, start, before, count ->
            binding.eTEditLastnameUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorLastname.text = ""
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

        binding.eTEditConfirmPasswordUser.setOnKeyListener(this)
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val openVerifyObserver = Observer<Unit> {
        findNavController().navigate(
            RegisterScreenDirections.actionRegisterScreenToVerifyScreen(
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
        } else binding.progress.root.visibility = View.GONE
    }

    private fun onClickBtnRegistration() {
        val firstname = binding.eTEditFirstnameUser.text.toString()
        val lastname = binding.eTEditLastnameUser.text.toString()
        val text = binding.mETEditPhoneUser.text.toString()
        val password = binding.eTEditPasswordUser.text.toString()
        val confirmPassword = binding.eTEditConfirmPasswordUser.text.toString()

        var phoneNumber = "+"
        if (text[0] == '+') {
            phoneNumber += text.filter {
                it.isDigit()

            }
        }
        if (firstname.length >= 3 && lastname.length >= 3 && phoneNumber.length == 13 && password.length >= 6 && confirmPassword == password) {
            phone = phoneNumber
            val data = RegisterRequest(firstname, lastname, phoneNumber, password)
            viewModel.register(data)
        } else {
            if (firstname.isEmpty()) {
                binding.eTEditFirstnameUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorFirstname.text = "Enter the firstname"
            } else if (firstname.length < 3) {
                binding.eTEditFirstnameUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorFirstname.text = "Firstname must be at least 3 letter"
            }
            if (lastname.isEmpty()) {
                binding.eTEditLastnameUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorLastname.text = "Enter the lastname"
            } else if (lastname.length < 3) {
                binding.eTEditLastnameUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorLastname.text = "Lastname must be at least 3 letter"
            }
            if (phoneNumber.length == 4) {
                binding.mETEditPhoneUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorPhone.text = "Enter the number"
            }
            if (phoneNumber.length in 5..12) {
                binding.mETEditPhoneUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tVErrorPhone.text = "Phone number entered incorrectly"
            }
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
        }
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        p2?.let {
            if (it.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER) {
                onClickBtnRegistration()
                return true
            }
        }
        return false
    }
}