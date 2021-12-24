package uz.gita.mobilbanking.data.source.remote.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.ResponseData
import uz.gita.mobilbanking.data.response.TokenResponse

interface AuthApi {

    @POST("/api/v1/auth/register")
    suspend fun userRegister(@Body request: RegisterRequest): Response<ResponseData<String>>

    @POST("/api/v1/auth/verify")
    suspend fun userVerify(@Body request: VerifyRequest): Response<ResponseData<TokenResponse>> // DataResponse bo'lishi kerak edi

    @POST("/api/v1/auth/login")
    suspend fun userLogin(@Body request: LoginRequest): Response<ResponseData<String>>

    @POST("/api/v1/auth/logout")
    suspend fun userLogout(@Header("token") token: String): Response<ResponseData<String>>

    @POST("/api/v1/auth/resend")
    suspend fun userResend(@Body request: LoginRequest): Response<ResponseData<String>>

    @POST("/api/v1/auth/reset")
    suspend fun userReset(@Body phone: ResetRequest): Response<ResponseData<String>>

    @POST("/api/v1/auth/newpassword")
    suspend fun userNewPassword(@Body request: NewPasswordRequest): Response<ResponseData<TokenResponse>>

    @POST("/api/v1/auth/refresh")
    suspend fun userRefreshTokenSuspend(
        @Header("refresh_token") refresh: String,
        @Body phone: ResetRequest
    ): Response<ResponseData<TokenResponse>>

    @POST("/api/v1/auth/refresh")
    fun userRefreshToken(
        @Header("refresh_token") refresh: String,
        @Body phone: ResetRequest
    ): Call<ResponseData<TokenResponse>>
}
/**

BASE_URL = "http://bd1a-82-215-100-195.ngrok.io"

 * register->(resend)->verify
 * login->(resend)->verify
 * reset->newpassword
 * logoutda headerda token berish kk
 * */
