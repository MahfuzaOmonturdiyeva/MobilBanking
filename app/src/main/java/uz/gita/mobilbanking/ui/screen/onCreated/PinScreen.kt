package uz.gita.mobilbanking.ui.screen.onCreated

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenPinBinding
import uz.gita.mobilbanking.ui.dialog.CustomDialog
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.onCreated.impl.PinViewModel1Impl

@AndroidEntryPoint
class PinScreen : Fragment(R.layout.screen_pin) {
    private val binding by viewBinding(ScreenPinBinding::bind)
    private var pin = ""
    private val viewModel: PinViewModel1Impl by viewModels()
    private var countUserEnteredCodes=0

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvTitle.text = "Enter the PIN"
        binding.btnChangePin.setOnClickListener {
            //       findNavController().navigate(PinScreenDirections.actionPinScreenToNewPinScreen())
        }
        binding.btnLogout.setOnClickListener {
            val dialog = CustomDialog.Builder(requireContext())
                .setTitle("Logout")
                .setDescription("If you log out, you will have to login again")
                .setCancelBtn(R.color.teal_500)
                .setOkBtn(R.color.red_400) {
                    viewModel.logout()
                }
            dialog.build().show()
        }

        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.openLoginLiveData.observe(this, openLoginLiveDataObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)

        binding.faBtn0.setOnClickListener {
            onChangedNumber("0")
        }
        binding.faBtn1.setOnClickListener {
            onChangedNumber("1")
        }
        binding.faBtn2.setOnClickListener {
            onChangedNumber("2")
        }
        binding.faBtn3.setOnClickListener {
            onChangedNumber("3")
        }
        binding.faBtn4.setOnClickListener {
            onChangedNumber("4")
        }
        binding.faBtn5.setOnClickListener {
            onChangedNumber("5")
        }
        binding.faBtn6.setOnClickListener {
            onChangedNumber("6")
        }
        binding.faBtn7.setOnClickListener {
            onChangedNumber("7")
        }
        binding.faBtn8.setOnClickListener {
            onChangedNumber("8")
        }
        binding.faBtn9.setOnClickListener {
            onChangedNumber("9")
        }
        binding.faBtnRemove.setOnClickListener {
            onRemovedNumber()
        }
    }

    private fun onChangedNumber(digit: String) {
        when (pin.length) {
            0 -> {
                pin += digit
                binding.imgvPin1.setImageResource(R.drawable.ic_circle)
            }
            1 -> {
                pin += digit
                binding.imgvPin2.setImageResource(R.drawable.ic_circle)
            }
            2 -> {
                pin += digit
                binding.imgvPin3.setImageResource(R.drawable.ic_circle)
            }
            3 -> {
                pin += digit
                binding.imgvPin4.setImageResource(R.drawable.ic_circle)
                if (viewModel.isCorrectPin(pin)) {
                    findNavController().navigate(PinScreenDirections.actionPinScreenToMainScreen2())
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.imgvPin1.animate().apply {
                            duration = 200
                            rotationYBy(360f)
                        }
                        binding.imgvPin2.animate().apply {
                            duration = 200
                            rotationYBy(360f)
                        }
                        binding.imgvPin3.animate().apply {
                            duration = 200
                            rotationYBy(360f)
                        }
                        binding.imgvPin4.animate().apply {
                            duration = 200
                            rotationYBy(360f)
                        }
                        binding.imgvPin1.setImageResource(R.drawable.ic_circle_line)
                        binding.imgvPin2.setImageResource(R.drawable.ic_circle_line)
                        binding.imgvPin3.setImageResource(R.drawable.ic_circle_line)
                        binding.imgvPin4.setImageResource(R.drawable.ic_circle_line)
                        pin = ""
                        showToast("Pincode is wrong")
                    }, 100)
                }
            }
            else -> {
                countUserEnteredCodes++;
                if(countUserEnteredCodes==4){
                    findNavController().navigate(PinScreenDirections.actionPinScreenToLoginScreen())
                }
                showToast("Number of attempts ${4-countUserEnteredCodes}")

                binding.imgvPin1.setImageResource(R.drawable.ic_circle_line)
                binding.imgvPin2.setImageResource(R.drawable.ic_circle_line)
                binding.imgvPin3.setImageResource(R.drawable.ic_circle_line)
                binding.imgvPin4.setImageResource(R.drawable.ic_circle_line)
                pin = ""
            }
        }
    }

    private fun onRemovedNumber() {
        when (pin.length) {
            4 -> {
                pin = pin.substring(0, 3)
                binding.imgvPin4.setImageResource(R.drawable.ic_circle_line)
            }
            3 -> {
                pin = pin.substring(0, 2)
                binding.imgvPin3.setImageResource(R.drawable.ic_circle_line)
            }
            2 -> {
                pin = pin.substring(0, 1)
                binding.imgvPin2.setImageResource(R.drawable.ic_circle_line)
            }
            1 -> {
                pin = ""
                binding.imgvPin1.setImageResource(R.drawable.ic_circle_line)
            }
        }
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val openLoginLiveDataObserver = Observer<Unit> {
        findNavController().navigate(PinScreenDirections.actionPinScreenToLoginScreen())
    }
    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }
    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progress.visibility = View.VISIBLE
        } else binding.progress.visibility = View.GONE
    }
}