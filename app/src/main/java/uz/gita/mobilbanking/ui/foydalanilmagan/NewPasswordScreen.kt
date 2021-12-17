package uz.gita.mobilbanking.ui.foydalanilmagan

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.request.NewPasswordRequest
import uz.gita.mobilbanking.databinding.ScreenNewPasswordBinding
import uz.gita.mobilbanking.utils.showToast

class NewPasswordScreen : Fragment(R.layout.screen_new_password) {
    private val binding by viewBinding(ScreenNewPasswordBinding::bind)
    private val viewModel: NewPasswordViewModel by viewModels()
    private var isEditPhone = true
    private var isEditPassword = false
    private var isEqualsPasswordAndConfirm = false
    private var isEditCode = false

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progress.hide()
        binding.btnNewPassword.isEnabled = false
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.successLoginLiveData.observe(this, successLoginObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        var password = ""
        var confirmPassword = ""
        var phoneNumber = "+"

        binding.btnNewPassword.setOnClickListener {
            val data = NewPasswordRequest(
                phoneNumber,
                binding.tIEditPasswordUser.text.toString(),
                binding.mEditCodeUser.text.toString()
            )
            viewModel.newPassword(data)
        }

        binding.mEditPhoneUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    val text = p0.toString()
                    if (text.length > 17) {
//                        binding.tIL.error = "No More!"
//                    } else if (text.length <= 17) {
//                        if (text.length == 17) {
//                            if (text[0] == '+') {
//                                phoneNumber += text.filter {
//                                    it.isDigit()
//                                }
//                            }
//                            isEditPhone = phoneNumber.length == 13
//                            isEnabled()
//                        }
//                        binding.tIL.error = null
                    }
                }
            }
        })

        binding.tIEditPasswordUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    val text = p0.toString()
                    if (text.length >= 6) {
//                        binding.tIL1.error = null
//                        binding.tIL1.setEndIconDrawable(R.drawable.ic_check_circle)
//                        isEditPassword = true
//                        password = text
//                        isEqualsPasswordAndConfirm = password == confirmPassword
//                        isEnabled()
//                    } else if (text.length < 6) {
//                        binding.tIL1.setEndIconDrawable(R.drawable.ic_close)
//                        binding.tIL1.error = "password should not be less than 6 letters *"
//                        isEditPassword = false
                    }
                }
            }
        })

        binding.tIEditConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    val text = p0.toString()
                    if (text.length >= 6) {
                        confirmPassword = text
                        isEqualsPasswordAndConfirm = password == confirmPassword
                        if (!isEqualsPasswordAndConfirm) {
                            binding.tIL2.error = "confirmation password did not match"
                            binding.tIL2.setEndIconDrawable(R.drawable.ic_close)
                        } else {
//                            binding.tIL2.setEndIconDrawable(R.drawable.ic_check_circle)
                            isEnabled()
                        }
                    } else if (text.length < 6) {
                        binding.tIL2.setEndIconDrawable(R.drawable.ic_close)
                        binding.tIL2.error = "password should not be less than 6 letters *"
                    }
                }
            }
        })
        binding.mEditCodeUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    val text = p0.toString()
                    if (text.length == 6) {
                        binding.tIL3.error = null
//                        binding.tIL3.setEndIconDrawable(R.drawable.ic_check_circle)
                        isEditCode = true
                        isEnabled()
                    } else if (text.length < 6) {
                        binding.tIL3.setEndIconDrawable(R.drawable.ic_close)
                        binding.tIL3.error = "code should not be less than 6 digits"
                        isEditCode = false
                    }
                }
            }
        })

    }

    private fun isEnabled() {
        if (isEditPhone && isEditPassword && isEqualsPasswordAndConfirm) {
            binding.btnNewPassword.isEnabled = true
        }
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val successLoginObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_registerScreen_to_verifyScreen)
    }
    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }
    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progress.show()
        } else binding.progress.hide()
    }
}