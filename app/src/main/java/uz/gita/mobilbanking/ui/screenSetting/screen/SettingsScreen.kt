package uz.gita.mobilbanking.ui.screenSetting.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.local.MyPref
import uz.gita.mobilbanking.databinding.ScreenSettingsBinding
import uz.gita.mobilbanking.ui.CustomDialog
import uz.gita.mobilbanking.ui.screenSetting.viewModelSetting.SettingsViewModel
import uz.gita.mobilbanking.utils.showToast

class SettingsScreen : Fragment(R.layout.screen_settings) {
    private val binding by viewBinding(ScreenSettingsBinding::bind)
    private val pref by lazy { MyPref }
    private val viewModel: SettingsViewModel by viewModels()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvUserNumber.text = pref.getInstance().numberUser
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.successLogoutLiveData.observe(this, successLogoutObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)

        binding.imgBtnClose.setOnClickListener {
            val count: Int = requireActivity().supportFragmentManager.backStackEntryCount
            if (count == 0) {
                requireActivity().onBackPressed()
            } else {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        binding.linePersonalInformation.setOnClickListener {
            findNavController().navigate(SettingsScreenDirections.actionSettingsScreenToPersonalScreen())
        }
        binding.lineSecurity.setOnClickListener {
            findNavController().navigate(SettingsScreenDirections.actionSettingsScreenToSecurityScreen())
        }
        binding.lineSupport.setOnClickListener {
            findNavController().navigate(SettingsScreenDirections.actionSettingsScreenToSupportScreen2())
        }
        binding.lineLogout.setOnClickListener {
            val dialog = CustomDialog(requireContext())
            dialog.setTitle("Logout")
            dialog.setDescription("If you log out, you will have to login again")
            dialog.setCancelBtn(R.color.teal_300)
            dialog.setOkBtn(R.color.red_400) {
                viewModel.logout()
            }
            dialog.show()
        }
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val successLogoutObserver = Observer<Unit> {
        findNavController().navigate(SettingsScreenDirections.actionSettingsScreenToLoginScreen())
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