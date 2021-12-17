package uz.gita.mobilbanking.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.mobilbanking.data.request.*
import uz.gita.mobilbanking.data.response.ResponseData
import uz.gita.mobilbanking.data.response.TokenResponse

interface CardApi {
    /**
     * POST: BASE_URL/api/v1/card/add-card
    HEADER: token
    BODY:
    {
    "pan":"8600124578455212",
    "exp":"02/25",
    "cardName":"Student"
    }
     */

    @POST("/api/v1/card/add-card")
    suspend fun addCard(
        @Header("token") accessTokenn: String,
        @Body addCardRequest: AddCardRequest
    ): Response<ResponseData<String>>
    // 401 qaytsa token eskirgan

    /**
     * POST: BASE_URL/api/v1/card/verify
    HEADER: token
    BODY:
    {
    "pan":"8600124578455212",
    "code":"206464"
    }
     */
    @POST("/api/v1/card/verify")
    suspend fun addCardVerify(
        @Header("token") accessToken: String,
        @Body addCardVerifyRequest: AddCardVerifyRequest
    ): Response<ResponseData<String>>

    /**
     * POST: BASE_URL/api/v1/card/edit-card
    HEADER: token
    BODY:
    {
    "cardNumber":"8600124578455210",
    "newName":"Ish2"
    }
     */
    @POST("/api/v1/card/edit-card")
    suspend fun editCard(
        @Header("token") accessToken: String,
        @Body editCardRequest: EditCardRequest
    ): Response<ResponseData<String>>

    /**
     * POST: BASE_URL/api/v1/card/delete-card
    HEADER: token
    BODY:
    {
    "cardNumber":"8600124578455210"
    }
     */
    @POST("/api/v1/card/delete-card")
    suspend fun deleteCard(
        @Header("token") accessToken: String,
        @Body cardNumberRequest: CardNumberRequest
    ): Response<ResponseData<String>>

    /**
    GET: BASE_URL/api/v1/card/all
    HEADER: token
     */
    @GET("/api/v1/card/all")
    suspend fun getAllCard(@Header("token") accessToken: String): Response<ResponseData<String>>

    /**
     *
    {
    "data": {
    "data": []
    }
    }
     */

    /**
     * GET: BASE_URL/api/v1/card/total-sum
    HEADER: token
     */
    @GET("/api/v1/card/total-sum")
    suspend fun getTotalSum(@Header("token") accessToken: String): Response<ResponseData<Double>>
//response "data": 0.0
}
/**
GET: BASE_URL/api/v1/card/owner-by-pan
HEADER: token
BODY:
{
"pan":"8600124578455210"
}

GET: BASE_URL/api/v1/card/owner-by-id
HEADER: token
BODY:
{
"id":"1"
}

PUT: BASE_URL/api/v1/card/color
HEADER: token
BODY:
{
"userCardId":1,
"color":8
}

PUT: BASE_URL/api/v1/card/ignore-balance
HEADER: token
BODY:
{
"userCardId":1,
"ignoreBalance":true
}

GET: BASE_URL/api/v1/card/total-sum
HEADER: token
 * */
