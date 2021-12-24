package uz.gita.mobilbanking.ui.screen.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.databinding.ScreenSettingsBinding
import uz.gita.mobilbanking.ui.dialog.CustomDialog
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.setting.impl.SettingViewModel1Impl

@AndroidEntryPoint
class SettingsScreen : Fragment(R.layout.screen_settings) {
    private val binding by viewBinding(ScreenSettingsBinding::bind)
    private val viewModel: SettingViewModel1Impl by viewModels()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvUserNumber.text = viewModel.phoneNumber
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.openLoginLiveData.observe(this, openLoginObserver)
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
            val dialog = CustomDialog.Builder(requireContext())
                .setTitle("Logout")
                .setDescription("If you log out, you will have to login again")
                .setCancelBtn(R.color.teal_500)
                .setOkBtn(R.color.red_400) {
                    viewModel.logout()
                }
            dialog.build().show()
        }
    }


    private val errorObserver = Observer<String> {
        showToast(it)
    }
    private val openLoginObserver = Observer<Unit> {
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