package uz.gita.mobilbanking.domain.usecase.setting.impl

import androidx.lifecycle.LiveData
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.ProfileRequest
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import uz.gita.mobilbanking.domain.repository.ProfileRepository
import uz.gita.mobilbanking.domain.usecase.setting.PersonalUseCase
import javax.inject.Inject

class PersonalUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
) : PersonalUseCase {
    override fun getAvatar(): LiveData<MyResult<Unit>> = profileRepository.getUserAvatar()

    override fun setAvatar(formdata: String): LiveData<MyResult<Unit>> =
        profileRepository.setUserAvatar(formdata)

    override fun getInfo(): LiveData<MyResult<ProfileInfoResponse>> =
        profileRepository.getUserProfileInfo()

    override fun setInfo(profileRequest: ProfileRequest): LiveData<MyResult<ProfileInfoResponse>> =
        profileRepository.setUserProfileInfo(profileRequest)
}