package uz.gita.mobilbanking.data.source.remote.api.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.*

interface CardApi {

    @POST("/api/v1/card/add-card")
    suspend fun addCard(
        @Body addCardRequest: AddCardRequest
    ): Response<ResponseData<String>>

    @POST("/api/v1/card/verify")
    suspend fun addCardVerify(
        @Body addCardVerifyRequest: AddCardVerifyRequest
    ): Response<ResponseData<CardInfoResponse>>

    @POST("/api/v1/card/edit-card")
    suspend fun editCard(
        @Body editCardRequest: EditCardRequest
    ): Response<ResponseData<String>>

    @POST("/api/v1/card/delete-card")
    suspend fun deleteCard(
        @Body cardNumberRequest: CardNumberRequest
    ): Response<ResponseData<String>>

    @GET("/api/v1/card/all")
    suspend fun getAllCard(): Response<ResponseData<List<CardInfoResponse>>>

    @GET("/api/v1/card/total-sum")
    suspend fun getTotalSum(): Response<ResponseData<Double>>
    //response "data": 0.0

    @GET("/api/v1/card/owner-by-pan")
    suspend fun ownerByPan(
        @Query("pan") pan: String,): Response<ResponseData<OwnerCardResponse>>

    @GET("/api/v1/card/owner-by-id")
    suspend fun ownerById(
        @Query("cardId") id: Int): Response<ResponseData<OwnerCardResponse>>

    @PUT("/api/v1/card/color")
    suspend fun colorCard(@Body colorRequest: ColorRequest): Response<ResponseData<ColorResponse>>

    @PUT("/api/v1/card/ignore-balance")
    suspend fun ignoreBalance(@Body ignoreBalanceRequest: IgnoreBalanceRequest): Response<ResponseData<IgnoreBalanceResponse>>
}
