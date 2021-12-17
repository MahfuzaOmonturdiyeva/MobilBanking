package uz.gita.mobilbanking.domain

import com.google.gson.Gson
import uz.gita.mobilbanking.data.ApiClient
import uz.gita.mobilbanking.data.api.AuthApi
import uz.gita.mobilbanking.data.local.MyPref
import uz.gita.mobilbanking.data.local.MyResult
import uz.gita.mobilbanking.data.request.*

class AuthRepository {
    private val pref = MyPref.getInstance()
    private val api = ApiClient.retrofit.create(AuthApi::class.java)

    suspend fun userRegister(data: RegisterRequest): MyResult<Unit> {
        try {
            val response = api.userRegister(data)
            if (response.isSuccessful) {
                pref.numberUser=data.phone
                response.body()?.message?.let {
                    return MyResult.Success(Unit)
                }
            }
            if (!response.isSuccessful) {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), String()::class.java)
                    return MyResult.Message(message)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }

            val body =
                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
            return body.message?.let { MyResult.Message(it) }!!
        } catch (e: Throwable) {
            return MyResult.Error(e)
        }
    }

    suspend fun userVerify(data: VerifyRequest): MyResult<Unit> {
        try {
            val response = api.userVerify(data)
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    pref.accessToken = it.accessToken
                    pref.refreshToken = it.refreshToken
                    return MyResult.Success(Unit)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }
            if (!response.isSuccessful) {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), String()::class.java)
                    return MyResult.Message(message)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }

            val body =
                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
            return body.message?.let { MyResult.Message(it) }!!
        } catch (e: Throwable) {
            return MyResult.Error(e)
        }
    }

    suspend fun userLogin(data: LoginRequest): MyResult<Unit> {
        try {
            val response = api.userLogin(data)

            if (response.isSuccessful) {

                response.body()?.message?.let {
                    pref.numberUser=data.phone
                    return MyResult.Success(Unit)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }
            if (!response.isSuccessful) {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), String()::class.java)
                    return MyResult.Message(message)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }

            val body =
                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
            return body.message?.let { MyResult.Message(it) }!!
        } catch (e: Throwable) {
            return MyResult.Error(e)
        }
    }

    suspend fun userLogout(): MyResult<Unit> {
        try {
            val response = api.userLogout(pref.accessToken)
            if (response.isSuccessful) {
                response.body()?.message?.let {
                    return MyResult.Success(Unit)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }
            if (!response.isSuccessful) {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), String()::class.java)
                    return MyResult.Message(message)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }

            val body =
                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
            return body.message?.let { MyResult.Message(it) }!!
        } catch (e: Throwable) {
            return MyResult.Error(e)
        }
    }

    suspend fun userResend(data: LoginRequest): MyResult<Unit> {
        try {
            val response = api.userResend(data)
            if (response.isSuccessful) {
                response.body()?.message?.let {
                    return MyResult.Success(Unit)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }
            if (!response.isSuccessful) {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), String()::class.java)
                    return MyResult.Message(message)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }

            val body =
                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
            return body.message?.let { MyResult.Message(it) }!!
        } catch (e: Throwable) {
            return MyResult.Error(e)
        }
    }

    suspend fun userReset(phone: String): MyResult<Unit> {
        try {
            val response = api.userReset(ResetRequest( phone))
            if (response.isSuccessful) {
                response.body()?.message?.let {
                    return MyResult.Success(Unit)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }
            if (!response.isSuccessful) {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), String()::class.java)
                    return MyResult.Message(message)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }

            val body =
                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
            return body.message?.let { MyResult.Message(it) }!!
        } catch (e: Throwable) {
            return MyResult.Error(e)
        }
    }

    suspend fun userNewPassword(data: NewPasswordRequest): MyResult<Unit> {
        try {
            val response = api.userNewPassword(data)
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    pref.accessToken = it.accessToken
                    pref.refreshToken = it.refreshToken
                    return MyResult.Success(Unit)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }
            if (!response.isSuccessful) {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), String()::class.java)
                    return MyResult.Message(message)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }

            val body =
                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
            return body.message?.let { MyResult.Message(it) }!!
        } catch (e: Throwable) {
            return MyResult.Error(e)
        }
    }
    suspend fun userRefreshToken(): MyResult<Unit> {
        try {
            val response = api.userAccessToken(pref.refreshToken, PhoneRequest(pref.numberUser))
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    pref.accessToken = it.accessToken
                    pref.refreshToken = it.refreshToken
                    return MyResult.Success(Unit)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }
            if (!response.isSuccessful) {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), String()::class.java)
                    return MyResult.Message(message)
                }
                return MyResult.Message("Server bilan bog'lanishda xatolik")
            }

            val body =
                response.body() ?: return MyResult.Message("Server bilan bog'lanishda xatolik")
            return body.message?.let { MyResult.Message(it) }!!
        } catch (e: Throwable) {
            return MyResult.Error(e)
        }
    }
}

