package uz.gita.mobilbanking.data.source.remote.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import uz.gita.mobilbanking.data.response.ResponseData

interface ProfileApi {

    @POST("/api/v1/profile/avatar")
    suspend fun setUserAvatar(
        @Body formData: String
    ): Response<ResponseData<String>>

    @GET("/api/v1/profile/avatar")
    suspend fun getUserAvatar(): Response<ResponseData<String>>

    @PUT("/api/v1/profile")
    suspend fun setUserProfileInfo(
        @Body profileRequest: ProfileRequest
    ): Response<ResponseData<ProfileInfoResponse>>

    @GET("/api/v1/profile/info")
    suspend fun getUserProfileInfo(): Response<ResponseData<ProfileInfoResponse>>
}