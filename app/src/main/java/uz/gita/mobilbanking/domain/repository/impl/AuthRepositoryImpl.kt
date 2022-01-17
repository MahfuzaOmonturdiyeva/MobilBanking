package uz.gita.mobilbanking.domain.repository.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import uz.gita.mobilbanking.data.common.MyResult
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.ResponseData
import uz.gita.mobilbanking.data.source.local.LocalStorage
import uz.gita.mobilbanking.data.source.remote.api.AuthApi
import uz.gita.mobilbanking.domain.repository.AuthRepository
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val localStorage: LocalStorage
) : AuthRepository {
    override val getPin: String
        get() = localStorage.pinCode
    override val getPhoneNumber: String
        get() = localStorage.phoneNumberUser

    override fun setPin(pin: String) {
        localStorage.pinCode = pin
    }

    override fun userRegister(data: RegisterRequest): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = authApi.userRegister(data)
            if (response.isSuccessful) {
                localStorage.phoneNumberUser = data.phone
                emit(MyResult.Success<Unit>(Unit))
            } else {
                if (response.code() == 409)
                    emit(MyResult.Message("Bunday foydalanuvchi allaqachon mavjud!"))
                emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun userVerify(data: VerifyRequest): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = authApi.userVerify(data)
            if (response.isSuccessful) {
                response.body()?.let {
                    it.data?.let {
                        localStorage.accessToken = it.accessToken
                        localStorage.refreshToken = it.refreshToken
                        localStorage.pinCode=""
                        emit(MyResult.Success<Unit>(Unit))
                    }
                }
            } else {
                emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun userLogin(data: LoginRequest): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = authApi.userLogin(data)
            if (response.isSuccessful) {
                localStorage.phoneNumberUser = data.phone
                emit(MyResult.Success<Unit>(Unit))
            } else {
                response.errorBody()?.let {
                    val response = Gson().fromJson(
                        it.string(),
                        ResponseData::class.java
                    )
                    emit(MyResult.Message<Unit>(response.message!!))
                    return@liveData
                }
                emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun userLogout(): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = authApi.userLogout()
            if (response.isSuccessful) {
                emit(MyResult.Success<Unit>(Unit))
            } else {
                emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun userResend(data: LoginRequest): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = authApi.userResend(data)
            if (response.isSuccessful) {
                emit(MyResult.Success<Unit>(Unit))
            } else {
                emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun userReset(phone: String): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = authApi.userReset(ResetRequest(phone))
            if (response.isSuccessful) {
                emit(MyResult.Success<Unit>(Unit))
            } else {
                response.errorBody()?.let {
                    val response = Gson().fromJson(
                        it.string(),
                        ResponseData::class.java
                    )
                    emit(MyResult.Message<Unit>(response.message!!))
                    return@liveData
                }
                emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun userNewPassword(data: NewPasswordRequest): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = authApi.userNewPassword(data)
            if (response.isSuccessful) {
                response.body()?.let {
                    it.data?.let {
                        localStorage.accessToken = it.accessToken
                        localStorage.refreshToken = it.refreshToken
                        emit(MyResult.Success<Unit>(Unit))
                    }
                }
            } else {
                emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }

    override fun userRefreshToken(): LiveData<MyResult<Unit>> = liveData {
        try {
            val response = authApi.userRefreshTokenSuspend(
                localStorage.refreshToken,
                ResetRequest(localStorage.phoneNumberUser)
            )
            if (response.isSuccessful) {
                response?.body()?.data?.let {
                    localStorage.accessToken = it.accessToken
                    localStorage.refreshToken = it.refreshToken
                    if (localStorage.pinCode!="")
                    emit(MyResult.Success<Unit>(Unit))
                    else emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
                    return@liveData
                }
                emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
            } else {
                if (response.code() == 401) {
                    emit(MyResult.Error<Unit>(InvalidTokenException()))
                } else {
                    emit(MyResult.Message<Unit>("server bilan bog'lanishda xatolik"))
                }
            }
        } catch (e: IOException) {
            emit(MyResult.Error<Unit>(e))
        }
    }
}

class InvalidTokenException(message: String = "Token xato") : Exception(message)