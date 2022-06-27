package uz.gita.mobilbanking.data.source.remote.api.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.gita.mobilbanking.data.request.SendMoneyRequest
import uz.gita.mobilbanking.data.response.HistoryResponse
import uz.gita.mobilbanking.data.response.ResponseData
import uz.gita.mobilbanking.data.response.SendMoneyResponse

interface TransferApi {

    @POST("/api/v1/money-transfer/send-money")
    suspend fun sendMoney(@Body data: SendMoneyRequest): Response<ResponseData<SendMoneyResponse>>

    @GET("/api/v1/money-transfer/fee")
    suspend fun fee(
        @Query("sender") sender: Int,
        @Query("amount") amount:Double
    ): Response<ResponseData<Double>>

    @GET("/api/v1/money-transfer/history")
    suspend fun history(
        @Query("page_number") page_number: Int,
        @Query("page_size") page_size: Int,
    ): Response<ResponseData<HistoryResponse>>

    @GET("/api/v1/money-transfer/outcomes")
    suspend fun outcomes(
        @Query("page_number") page_number: Int,
        @Query("page_size") page_size: Int,
    ): Response<ResponseData<HistoryResponse>>

    @GET("/api/v1/money-transfer/incomes")
    suspend fun incomes(
        @Query("page_number") page_number: Int,
        @Query("page_size") page_size: Int,
    ): Response<ResponseData<HistoryResponse>>
}
