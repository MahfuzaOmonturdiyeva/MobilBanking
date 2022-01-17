package uz.gita.mobilbanking.ui.screen.card

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.request.AddCardRequest
import uz.gita.mobilbanking.databinding.ScreenCardAddCardBinding
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.card.impl.AddCardViewModelImpl

@AndroidEntryPoint
class AddCardScreen : Fragment(R.layout.screen_card_add_card) {
    private val binding by viewBinding(ScreenCardAddCardBinding::bind)
    private val viewModel: AddCardViewModelImpl by viewModels()
    private var isFavorite: Boolean = false
    private var pan = ""

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.openAllCardsLiveData.observe(this, openAllCardsObServer)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)

        binding.metEditPanCard.doOnTextChanged { text, start, before, count ->
            binding.metEditPanCard.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tvErrorPanCard.text = ""
        }
        binding.metEditValidityCard.doOnTextChanged { text, start, before, count ->
            binding.metEditValidityCard.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tvErrorValidity.text = ""
        }
        binding.imgBtnClose.setOnClickListener {
            closeScreen()
        }
        binding.imgBtnFavoriteCardChecked.setOnClickListener {
            clickFavoriteBtn()
        }
        binding.btnAddCard.setOnClickListener {
            addCard()
        }

    }

    private val openAllCardsObServer = Observer<Unit> {
        if (isFavorite)
            findNavController().navigate(
                AddCardScreenDirections.actionAddCardScreenToAllCardsScreen(
                    pan
                )
            )
        else {
            findNavController().navigate(
                AddCardScreenDirections.actionAddCardScreenToAllCardsScreen(
                    ""
                )
            )
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
        val count: Int = requireActivity().supportFragmentManager.backStackEntryCount
        if (count == 0) {
            requireActivity().onBackPressed()
        } else {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun clickFavoriteBtn() {
        isFavorite = if (!isFavorite) {
            binding.imgBtnFavoriteCardChecked.setImageResource(R.drawable.ic_circle)
            true
        } else {
            binding.imgBtnFavoriteCardChecked.setImageResource(R.drawable.ic_circle_line_grey)
            false
        }
    }

    private fun addCard() {
        pan = binding.metEditPanCard.text.toString()
        val exp = binding.metEditValidityCard.text.toString()
        val cardName = binding.etEditNameCard.text.toString()

        pan = pan.filter {
            it != ' '
        }
        showToast(pan)
        if (pan.length == 16 && exp.length == 5) {
            viewModel.addOneCard(AddCardRequest(pan, exp, cardName))
        } else {
            if (pan.isEmpty()) {
                binding.metEditPanCard.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tvErrorPanCard.text = "Enter card number"
            } else {
                binding.metEditPanCard.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tvErrorPanCard.text =
                    "Card number should not be less than 16 digits"
            }
            if (exp.isEmpty()) {
                binding.metEditValidityCard.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tvErrorValidity.text = "Enter validity card"
            } else {
                binding.metEditValidityCard.setBackgroundResource(R.drawable.background_custom_edittext_error)
                binding.tvErrorValidity.text = "Enter the card validity period correctly"
            }
        }
    }
}