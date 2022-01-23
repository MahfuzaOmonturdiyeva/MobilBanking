package uz.gita.mobilbanking.data.source.remote.api.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import uz.gita.mobilbanking.data.response.ResponseData
import okhttp3.MultipartBody
import retrofit2.http.POST
import retrofit2.http.Multipart


interface ProfileApi {

    @Multipart
    @POST("/api/v1/profile/avatar")
    suspend fun setUserAvatar(
        @Part image: MultipartBody.Part,
    ): Response<ResponseData<Unit>>

    @GET("/api/v1/profile/photo-url")
    suspend fun getUserAvatar(): Response<String>

    @PUT("/api/v1/profile")
    suspend fun setUserProfileInfo(
        @Body profileRequest: ProfileRequest
    ): Response<ResponseData<ProfileInfoResponse>>

    @GET("/api/v1/profile/info")
    suspend fun getUserProfileInfo(): Response<ResponseData<ProfileInfoResponse>>
}