package uz.gita.mobilbanking.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.ProfileRequest
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import uz.gita.mobilbanking.data.source.remote.api.ProfileApi
import uz.gita.mobilbanking.domain.repository.ProfileRepository
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

class ProfileRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi
) : ProfileRepository {
    override fun setUserAvatar(file:File): LiveData<MyResult<Unit>> = liveData {
        try {
            val requestFile=RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
            val body=MultipartBody.Part.createFormData("avatar", file.name, requestFile)
            val response = profileApi.setUserAvatar(body)
            if (response.isSuccessful) {
                emit(MyResult.Success<Unit>(Unit))
            } else {
//                response.errorBody()?.let {
//                    val message = Gson().fromJson(it.string(), String()::class.java)
//                    emit(MyResult.Message<Unit>(message))
//                    return@liveData
//                }
                emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun getUserAvatar(): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = profileApi.getUserAvatar()
            if (response.isSuccessful) {
                emit(MyResult.Success<Unit>(Unit))
            } else {
//                response.errorBody()?.let {
//                    val message = Gson().fromJson(it.string(), String()::class.java)
//                    emit(MyResult.Message<Unit>(message))
//                    return@liveData
//                }
                emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun setUserProfileInfo(profileRequest: ProfileRequest): LiveData<MyResult<ProfileInfoResponse>> =
        liveData {
            try {
                val response = profileApi.setUserProfileInfo(profileRequest)
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        emit(MyResult.Success<ProfileInfoResponse>(it))
                        return@liveData
                    }
                } else {
//                    response.errorBody()?.let {
//                        val message = Gson().fromJson(it.string(), String()::class.java)
//                        emit(MyResult.Message<ProfileInfoResponse>(message))
//                        return@liveData
//                    }
                    emit(MyResult.Message<ProfileInfoResponse>("server bilan bog'lanishda xatolik"))
                }
            } catch (e: IOException) {
                emit(MyResult.Error<ProfileInfoResponse>(e))
            }
        }

    override fun getUserProfileInfo(): LiveData<MyResult<ProfileInfoResponse>> = liveData {
        try {
            val response = profileApi.getUserProfileInfo()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    emit(MyResult.Success<ProfileInfoResponse>(it))
                    return@liveData
                }
            } else {
//                response.errorBody()?.let {
//                    val message = Gson().fromJson(it.string(), String()::class.java)
//                    emit(MyResult.Message<ProfileInfoResponse>(message))
//                    return@liveData
//                }
                emit(MyResult.Message<ProfileInfoResponse>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<ProfileInfoResponse>(e))
        }
    }
}