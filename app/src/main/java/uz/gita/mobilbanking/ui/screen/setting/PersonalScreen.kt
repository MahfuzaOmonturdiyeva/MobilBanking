package uz.gita.mobilbanking.ui.screen.setting

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.request.ProfileRequest
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import uz.gita.mobilbanking.databinding.ScreenSettingsPersonalBinding
import uz.gita.mobilbanking.utils.showToast
import uz.gita.mobilbanking.viewmodel.setting.impl.PersonalViewModel1Impl
import java.io.File
import java.io.IOException
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions


@AndroidEntryPoint
class PersonalScreen : Fragment(R.layout.screen_settings_personal) {
    private val binding by viewBinding(ScreenSettingsPersonalBinding::bind)
    private val viewModel: PersonalViewModel1Impl by viewModels()
    private var password = ""
    private var photoFile: File? = null

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.notSuccessGetAvatarLiveData.observe(this,notSuccessGetAvatarObserver )
        viewModel.joinInfoLiveData.observe(this, joinInfoObserver)
        viewModel.joinAvatarLiveData.observe(this, joinAvatarObserver)
        viewModel.notConnectionLiveData.observe(this, notConnectionObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.successSetAvatarLiveData.observe(this, successSetAvatarObserver)

//        Glide
//            .with(this)
//            .load("http://127.0.0.1:8080/api/v1/profile/photo?u=9d8f296f-b034-40e2-a26d-96b0fceaa098.jpg")
//            .centerCrop()
//            .into(binding.cImgProfileImage);
        binding.cImgProfileImage.setOnClickListener {
            Permissions.check(
                requireContext()/*context*/,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                null,
                object : PermissionHandler() {
                    override fun onGranted() {
                        val intent =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        intent.type = "image/*"
                        startActivityForResult(intent, 1)
                    }
                })
        }
        binding.btnSetAvatar.setOnClickListener {
            if (photoFile == null) {
                showToast("Upload your avatar!")
            } else {
                viewModel.setAvatar(photoFile!!)
            }
        }

        binding.btnSave.setOnClickListener {
            onSave()
        }
        binding.imgBtnClose.setOnClickListener {
            val count: Int = requireActivity().supportFragmentManager.backStackEntryCount
            if (count == 0) {
                requireActivity().onBackPressed()
            } else {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        binding.eTEditFirstnameUser.doOnTextChanged { text, start, before, count ->
            binding.eTEditFirstnameUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorFirstname.text = ""
        }
        binding.eTEditLastnameUser.doOnTextChanged { text, start, before, count ->
            binding.eTEditLastnameUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorLastname.text = ""
        }
        binding.eTEditConfirmPasswordUser.doOnTextChanged { text, start, before, count ->
            binding.eTEditConfirmPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVErrorConfirmPassword.text = ""
        }
        binding.eTEditOldPasswordUser.doOnTextChanged { text, start, before, count ->
            binding.eTEditOldPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVOldErrorPassword.text = ""
        }
        binding.eTEditNewPasswordUser.doOnTextChanged { text, start, before, count ->
            binding.eTEditNewPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext)
            binding.tVNewErrorPassword.text = ""
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                try {
                    val path = getRealPathFromURI(it, requireActivity())
                    if (path != null) {
                        photoFile = File(path)
                        val photo = MediaStore.Images.Media.getBitmap(context?.contentResolver, it)
                        binding.cImgProfileImage.setImageBitmap(photo)
                    } else {
                        showToast("Qaytadan urinib ko'ring")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getRealPathFromURI(contentURI: Uri?, context: Activity): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.managedQuery(
            contentURI, projection, null,
            null, null
        ) ?: return null
        val columnIndex = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        return if (cursor.moveToFirst()) cursor.getString(columnIndex) else null
    }

    private val notSuccessGetAvatarObserver = Observer<String> {

    }


    private val successSetAvatarObserver = Observer<Unit> {
        showToast("rasm yuklandi!")
    }

    private val joinAvatarObserver = Observer<String> {
        Glide
            .with(this)
            .load(it)
            .centerCrop()
            .into(binding.cImgProfileImage);
    }

    private val messageObserver = Observer<String> {
        showToast(it)
    }
    private val errorObserver = Observer<String> {
        showToast(it)
        Handler(Looper.getMainLooper()).postDelayed({
            val count: Int = requireActivity().supportFragmentManager.backStackEntryCount
            if (count == 0) {
                requireActivity().onBackPressed()
            } else {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }, 100)

    }
    private val joinInfoObserver = Observer<ProfileInfoResponse> {
        binding.eTEditFirstnameUser.setText(it.firstName)
        binding.eTEditLastnameUser.setText(it.lastName)
        password = it.password
        binding.eTEditConfirmPasswordUser.setText("")
        binding.eTEditNewPasswordUser.setText("")
        binding.eTEditOldPasswordUser.setText("")
    }
    private val notConnectionObserver = Observer<String> {
        showToast(it)
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progress.visibility = View.VISIBLE
        } else binding.progress.visibility = View.GONE
    }

    private fun onSave() {
        val firstname = binding.eTEditFirstnameUser.text.toString()
        val lastname = binding.eTEditLastnameUser.text.toString()
        val oldPassword = binding.eTEditOldPasswordUser.text.toString()
        val newPassword = binding.eTEditNewPasswordUser.text.toString()
        val confirmPassword = binding.eTEditConfirmPasswordUser.text.toString()

        if (firstname.length >= 3 && lastname.length >= 3 && oldPassword == password && confirmPassword.isNotEmpty() && confirmPassword == newPassword) {
            val data = ProfileRequest(firstname, lastname, newPassword)
            viewModel.setInfo(data)
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
            when {
                oldPassword.isEmpty() -> {
                    binding.eTEditOldPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVOldErrorPassword.text = "Enter the old password"
                }
                oldPassword.length < 6 -> {
                    binding.eTEditOldPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVOldErrorPassword.text = "Password must be at least 6 characters"
                }
                oldPassword != password -> {
                    binding.eTEditOldPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVOldErrorPassword.text = "Password entered incorrectly"
                }
                newPassword.isEmpty() -> {
                    binding.eTEditNewPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVNewErrorPassword.text = "Enter the new password"
                }
                newPassword.length < 6 -> {
                    binding.eTEditNewPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVNewErrorPassword.text = "Password must be at least 6 characters"
                }
                confirmPassword.isEmpty() -> {
                    binding.eTEditConfirmPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVErrorConfirmPassword.text = "Enter the confirm password"
                }
                newPassword != confirmPassword -> {
                    binding.eTEditConfirmPasswordUser.setBackgroundResource(R.drawable.background_custom_edittext_error)
                    binding.tVErrorConfirmPassword.text =
                        "Confirmation password is not compatible with the new password"
                }
            }
        }
    }
}