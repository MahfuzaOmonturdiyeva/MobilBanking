package uz.gita.mobilbanking.data

import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.mobilbanking.app.App
import java.util.concurrent.TimeUnit

object ApiClient {
    private val client=OkHttpClient.Builder()
        .addInterceptor(ChuckInterceptor(App.instance))
        .readTimeout(120, TimeUnit.SECONDS)
        .connectTimeout(120, TimeUnit.SECONDS)
        .build()

    val retrofit=Retrofit.Builder()
        .client(client)
        .baseUrl("http://7838-185-163-26-165.ngrok.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}