package uz.gita.mobilbanking.ui.screen.card

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.data.response.ColorResponse
import uz.gita.mobilbanking.data.response.IgnoreBalanceResponse
import uz.gita.mobilbanking.databinding.ScreenCardOneCardBinding
import uz.gita.mobilbanking.viewmodel.card.impl.AllCardsViewModelImpl
import uz.gita.mobilbanking.utils.showToast


import android.content.ClipData
import android.content.ClipboardManager
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.gita.mobilbanking.data.request.CardNumberRequest
import uz.gita.mobilbanking.data.request.ColorRequest
import uz.gita.mobilbanking.data.request.EditCardRequest
import uz.gita.mobilbanking.data.request.IgnoreBalanceRequest
import uz.gita.mobilbanking.ui.dialog.CustomDialog


@AndroidEntryPoint
class OneCardScreen : Fragment(R.layout.screen_card_one_card) {
    private val binding by viewBinding(ScreenCardOneCardBinding::bind)
    private val viewModel: AllCardsViewModelImpl by viewModels()
    private lateinit var cardInfoResponse: CardInfoResponse
    private var isFavoriteCard = false
    private var cardColor = 0
    private var isEditCardName = false

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.successIgnoreBalanceLiveData.observe(this, successIgnoreBalanceObserver)
        viewModel.successColorCardLiveData.observe(this, successColorCardObserver)
        viewModel.successEditCardLiveData.observe(this, successEditCardObserver)
        viewModel.successDeleteCardLiveData.observe(this, successDeleteCardObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.provideCardInfoResponseLiveData.observe(
            viewLifecycleOwner,
            provideCardInfoResponseObserver
        )
        isFavoriteCard = viewModel.favoriteCardId == cardInfoResponse.id
        cardColor = cardInfoResponse.color
        if (isFavoriteCard)
            binding.imgBtnFavoriteCard.setImageResource(R.drawable.ic_star)

        binding.imgBtnEditNameCard.setOnClickListener {
            isEditCardName = true
            editCardName()
        }

        binding.imgvCopyPan.setOnClickListener {
            copyClipBoard()
        }
        binding.imgBtnEyeIsShowBalance.setOnClickListener {
            if (!cardInfoResponse.ignoreBalance) {
                viewModel.ignoreBalance(IgnoreBalanceRequest(cardInfoResponse.id, true))
            } else viewModel.ignoreBalance(IgnoreBalanceRequest(cardInfoResponse.id, false))
        }
        binding.imgBtnFavoriteCard.setOnClickListener {
            isFavoriteCardChange()
        }
        binding.imgBtnClose.setOnClickListener {
            if (isFavoriteCard) {
                viewModel.favoriteCardId = cardInfoResponse.id
            }
            if (isEditCardName) {
                viewModel.editCard(
                    EditCardRequest(
                        cardInfoResponse.pan,
                        binding.etNameCardItem.text.toString()
                    )
                )
            }
            findNavController().navigate(OneCardScreenDirections.actionOneCardScreenToAllCardsScreen())
        }
        binding.btnDeleteCard.setOnClickListener {
            val dialog = CustomDialog.Builder(requireContext())
                .setTitle("Delete")
                .setDescription("Do you really want to delete your card")
                .setCancelBtn(R.color.teal_500)
                .setOkBtn(R.color.red_400) {
                    viewModel.deleteCard(CardNumberRequest(cardInfoResponse.pan))
                }
            dialog.build().show()
        }
        binding.btnChangeColor.setOnClickListener {
            changeColorCard()
        }
    }

    private fun changeColorCard() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(
            R.layout.custom_dialog_bottom_sheet,
            requireActivity().findViewById(R.id.bottom_sheet_container) as LinearLayout
        )
        view.findViewById<Button>(R.id.btn_color0).setOnClickListener {
            binding.cvOneCard.setBackgroundResource(R.color.white)
            cardColor = 0
        }
        view.findViewById<Button>(R.id.btn_color1).setOnClickListener {
            binding.cvOneCard.setBackgroundResource(R.color.card_color1)
            cardColor = 1
        }
        view.findViewById<Button>(R.id.btn_color2).setOnClickListener {
            binding.cvOneCard.setBackgroundResource(R.color.card_color2)
            cardColor = 2
        }
        view.findViewById<Button>(R.id.btn_color3).setOnClickListener {
            binding.cvOneCard.setBackgroundResource(R.color.card_color3)
            cardColor = 3
        }
        view.findViewById<Button>(R.id.btn_color4).setOnClickListener {
            binding.cvOneCard.setBackgroundResource(R.color.card_color4)
            cardColor = 4
        }
        view.findViewById<Button>(R.id.btn_color5).setOnClickListener {
            binding.cvOneCard.setBackgroundResource(R.color.card_color5)
            cardColor = 5
        }
        view.findViewById<Button>(R.id.btn_color6).setOnClickListener {
            binding.cvOneCard.setBackgroundResource(R.color.card_color6)
            cardColor = 6
        }
        view.findViewById<Button>(R.id.btn_color7).setOnClickListener {
            binding.cvOneCard.setBackgroundResource(R.color.card_color7)
            cardColor = 7
        }
        view.findViewById<Button>(R.id.btn_set_card_color).setOnClickListener {
            viewModel.colorCard(ColorRequest(cardInfoResponse.id, cardColor))
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun isFavoriteCardChange() {
        isFavoriteCard = if (isFavoriteCard) {
            binding.imgBtnFavoriteCard.setImageResource(R.drawable.ic_star_border)
            false
        } else {
            binding.imgBtnFavoriteCard.setImageResource(R.drawable.ic_star)
            true
        }
    }

    private fun editCardName() {
        showToast("Edit name card")
        binding.etNameCardItem.requestFocus()
        binding.etNameCardItem.isFocusable = true
        binding.etNameCardItem.isFocusableInTouchMode = true

        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etNameCardItem, InputMethodManager.SHOW_FORCED)

    }

    private fun copyClipBoard() {
        val clipboard: ClipboardManager? =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText(cardInfoResponse.pan, cardInfoResponse.pan)
        clip?.let {
            clipboard?.setPrimaryClip(clip)
            showToast("Card number copied")
        }
    }

    private val provideCardInfoResponseObserver = Observer<CardInfoResponse> {
        it?.let {
            cardInfoResponse = it

            binding.imgHumo.visibility = View.GONE
            binding.etNameCardItem.setText(it.cardName)
            binding.tvValidityDateItem.text = it.exp
            var textPan = ""
            it.pan.forEachIndexed { index, c ->
                if (index <= 5 || index >= 12)
                    textPan += c.toString()
            }

            binding.tvPanCardItem.text = textPan
            setColorCard(it.color)
            if (!it.ignoreBalance) {
                binding.imgBtnEyeIsShowBalance.setImageResource(R.drawable.ic_eye_teal_500)
                binding.tvBalanceCardItem.text = it.balance.toString()
                binding.tvTextUzs.visibility = View.VISIBLE
            } else {
                binding.imgBtnEyeIsShowBalance.setImageResource(R.drawable.ic_eye)
                binding.tvBalanceCardItem.text = "Balance not enabled"
                binding.tvTextUzs.visibility = View.INVISIBLE
            }
        }
    }

    private val successDeleteCardObserver = Observer<Unit> {
        findNavController().navigate(OneCardScreenDirections.actionOneCardScreenToAllCardsScreen())
    }
    private val successEditCardObserver = Observer<Unit> {

    }
    private val successColorCardObserver = Observer<ColorResponse> {
        setColorCard(it.color)
    }

    private fun setColorCard(colorCard: Int) {
        when (colorCard) {
            0 -> binding.cvOneCard.setBackgroundResource(R.color.white)
            1 -> binding.cvOneCard.setBackgroundResource(R.color.card_color1)
            2 -> binding.cvOneCard.setBackgroundResource(R.color.card_color2)
            3 -> binding.cvOneCard.setBackgroundResource(R.color.card_color3)
            4 -> binding.cvOneCard.setBackgroundResource(R.color.card_color4)
            5 -> binding.cvOneCard.setBackgroundResource(R.color.card_color5)
            6 -> binding.cvOneCard.setBackgroundResource(R.color.card_color6)
            7 -> binding.cvOneCard.setBackgroundResource(R.color.card_color7)
        }
    }

    private val successIgnoreBalanceObserver = Observer<IgnoreBalanceResponse> {
        it?.let {
            if (!it.ignoreBalance) {
                binding.imgBtnEyeIsShowBalance.setImageResource(R.drawable.ic_eye_teal_500)
                binding.tvBalanceCardItem.text = cardInfoResponse.balance.toString()
                binding.tvTextUzs.visibility = View.VISIBLE
            } else {
                binding.imgBtnEyeIsShowBalance.setImageResource(R.drawable.ic_eye)
                binding.tvBalanceCardItem.text = "Balance not enabled"
                binding.tvTextUzs.visibility = View.INVISIBLE
            }
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
}