package uz.gita.mobilbanking.ui.screen.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.source.local.LocalStorage
import uz.gita.mobilbanking.databinding.ScreenPinBinding

@AndroidEntryPoint
class NewPinScreen : Fragment(R.layout.screen_pin) {
    private val binding by viewBinding(ScreenPinBinding::bind)
    private var pin = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvTitle.text = "Enter a new PIN"
        binding.btnChangePin.visibility = View.INVISIBLE
        binding.btnLogout.visibility = View.INVISIBLE

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
                findNavController().navigate(NewPinScreenDirections.actionNewPinScreenToConFirmPinScreen(pin))
            }
            else->{
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
}