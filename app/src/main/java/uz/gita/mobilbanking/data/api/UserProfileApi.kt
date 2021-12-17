package uz.gita.mobilbanking.data.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.ProfileInfoResponse
import uz.gita.mobilbanking.data.response.ResponseData
import uz.gita.mobilbanking.data.response.TokenResponse

interface UserProfileApi {

    @POST("/api/v1/profile/avatar")
    suspend fun setUserAvatar(
        @Header("token") accesToken: String,
        @Body formData: String
    ): Response<ResponseData<String>>

    @GET("/api/v1/profile/avatar")
    suspend fun getUserAvatar(@Header("token") accesToken: String): Response<ResponseData<String>>

    @PUT("/api/v1/profile")
    suspend fun setUserProfileInfo(
        @Header("token") accesToken: String,
        @Body profileRequest: ProfileRequest
    ): Response<ResponseData<ProfileInfoResponse>>
//token eskirgan qaytishi 401

    @GET("/api/v1/profile/info")
    suspend fun getUserProfileInfo(@Header("token") accesToken: String): Response<ResponseData<ProfileInfoResponse>>
}
/**
 *POST: BASE_URL/api/v1/profile/avatar
HEADER: token
BODY: form-data -> avatar


GET: BASE_URL/api/v1/profile/avatar
HEADER: token

PUT: BASE_URL/api/v1/profile
HEADER: token
BODY:
{
"firstName": null,
"lastName": null,
"password": "qwertyuiop"
}

GET: BASE_URL/api/v1/profile/info
HEADER: token
 */