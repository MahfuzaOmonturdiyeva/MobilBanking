package uz.gita.mobilbanking.ui.foydalanilmagan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenAuthLoginBinding
import uz.gita.mobilbanking.utils.showToast

class ResendScreen: Fragment(R.layout.screen_auth_login) {
    private val binding by viewBinding(ScreenAuthLoginBinding::bind)
    private val viewModel: ResendViewModel by viewModels()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.progress.hide()
        binding.btnLogin.text="Resend"
        binding.btnRegister.visibility=View.INVISIBLE
        binding.btnRegister.isClickable=false
        binding.btnForgotPassword.visibility=View.INVISIBLE
        binding.btnForgotPassword.isClickable=false
//        binding.tVLogin.text="Resend"
        binding.btnLogin.isEnabled = false
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.successLoginLiveData.observe(this, successLoginObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        var isEditPhone = true
        var isEditPassword = false
        var phoneNumber = "+"

        binding.btnLogin.setOnClickListener {
//            val data = LoginRequest(phoneNumber, binding.mEditPhoneUser.text.toString())
//            viewModel.resend(data)
        }

//        binding.mEditPhoneUser.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                p0?.let {
//                    val text = p0.toString()
////                    if (text.length > 17) {
////                        binding.tIL.error = "No More!"
////                    } else if (text.length <= 17) {
////                        if (text.length == 17) {
////                            if (text[0] == '+') {
////                                phoneNumber += text.filter {
////                                    it.isDigit()
////                                }
////                            }
////                            isEditPhone = phoneNumber.length == 13
////                            binding.btnLogin.isEnabled = isEditPhone && isEditPassword
////                        }
////                        binding.tIL.error = null
////                    }
//                }
//            }
//        })
//
//        binding.tIEditPasswordUser.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }

//            override fun afterTextChanged(p0: Editable?) {
//                p0?.let {
//                    val text = p0.toString()
//                    if (text.length >= 6) {
//                        binding.tIL1.error = null
//                        binding.tIL1.setEndIconDrawable(R.drawable.ic_check_circle)
//                        isEditPassword = true
//                        binding.btnLogin.isEnabled = isEditPhone && isEditPassword
//                    } else if (text.length < 6) {
//                        binding.tIL1.setEndIconDrawable(R.drawable.ic_close)
//                        binding.tIL1.error = "password should not be less than 6 letters *"
//                        isEditPassword = false
//                    }
//                }
//            }
//        })

    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val successLoginObserver = Observer<Unit> {
//        findNavController().navigate(R.id.action_resendScreen_to_verifyScreen)
    }
    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }
    private val progressObserver = Observer<Boolean> {
//        if (it) {
//            binding.progress.show()
//        } else binding.progress.hide()
    }
}