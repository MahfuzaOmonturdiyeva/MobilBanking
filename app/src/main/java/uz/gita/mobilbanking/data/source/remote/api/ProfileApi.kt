package uz.gita.mobilbanking.data.source.remote.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import uz.gita.mobilbanking.data.response.ResponseData
import okhttp3.RequestBody

import okhttp3.MultipartBody

import okhttp3.ResponseBody

import retrofit2.http.POST

import retrofit2.http.Multipart




interface ProfileApi {

    @Multipart
    @POST("/api/v1/profile/avatar")
    suspend fun setUserAvatar(
        @Part image: MultipartBody.Part,
    ): Response<ResponseData<Unit>>
//    @POST("/api/v1/profile/avatar")
//    suspend fun setUserAvatar(
//        @Body formData: String
//    ): Response<ResponseData<String>>

    @GET("/api/v1/profile/avatar")
    suspend fun getUserAvatar(): Response<ResponseData<String>>

    @PUT("/api/v1/profile")
    suspend fun setUserProfileInfo(
        @Body profileRequest: ProfileRequest
    ): Response<ResponseData<ProfileInfoResponse>>

    @GET("/api/v1/profile/info")
    suspend fun getUserProfileInfo(): Response<ResponseData<ProfileInfoResponse>>
}