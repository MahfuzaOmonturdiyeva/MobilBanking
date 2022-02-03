package uz.gita.mobilbanking.ui.screen.card

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.databinding.ScreenCardAllCardsBinding
import uz.gita.mobilbanking.ui.adapter.CardAdapter
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.card.impl.AllCardsViewModelImpl

@AndroidEntryPoint
class AllCardsScreen : Fragment(R.layout.screen_card_all_cards) {
    private val binding by viewBinding(ScreenCardAllCardsBinding::bind)
    private val viewModel: AllCardsViewModelImpl by activityViewModels<AllCardsViewModelImpl>()
    private lateinit var adapter: CardAdapter

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllCard()
        adapter=CardAdapter(){
            viewModel.setValueCardInfoResponse(it)
            findNavController().navigate(AllCardsScreenDirections.actionAllCardsScreenToOneCardScreen())
        }
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.notConnectionLiveData.observe(this,notConnectionObserver)
        viewModel.joinAllCardsLiveData.observe(this, joinAllCardsObserver)
        viewModel.errorLiveData.observe(this,errorObserver )
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.provideCardInfoResponseLiveData.observe(viewLifecycleOwner, provideCardInfoResponseObserver)
        viewModel.logoutLiveData.observe(this, logoutObserver)

        binding.containerCards.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.containerCards.adapter=adapter
        binding.lineAddCard.setOnClickListener {
            findNavController().navigate(AllCardsScreenDirections.actionAllCardsScreenToAddCardScreen())
        }
        binding.imgBtnClose.setOnClickListener {
            findNavController().navigate(AllCardsScreenDirections.actionGlobalMainScreen2())
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(AllCardsScreenDirections.actionGlobalMainScreen2())
        }
    }
    private val logoutObserver= Observer<Unit> {
        findNavController().navigate(AllCardsScreenDirections.actionGlobalLoginScreen())
    }
    private val joinAllCardsObserver= Observer<List<CardInfoResponse>> {
        it?.let {
            var cardInfoResponse:CardInfoResponse?=null
            if (viewModel.favoriteCardId!=-1){
                for(i in it){
                    if(i.id==viewModel.favoriteCardId){
                        cardInfoResponse=i
                        break
                    }
                }
            }
            if(cardInfoResponse!=null){
                val array=it as ArrayList
                array.remove(cardInfoResponse)
                array.add(0, cardInfoResponse)
                adapter.submitList(array)
            }else
            adapter.submitList(it)
        }
    }
    private val provideCardInfoResponseObserver= Observer<CardInfoResponse> {  }

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