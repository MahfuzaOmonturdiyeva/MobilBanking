package uz.gita.mobilbanking.di

import android.content.Context
import com.readystatesoftware.chuck.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.mobilbanking.BuildConfig.BASE_URL
import uz.gita.mobilbanking.data.request.ResetRequest
import uz.gita.mobilbanking.data.source.local.LocalStorage
import uz.gita.mobilbanking.data.source.remote.api.AuthApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    val BASE_URL="http://6146-185-163-27-54.ngrok.io"
    @[Singleton Provides]
    fun getRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @[Singleton Provides]
    fun getHttpClient(
        localStorage: LocalStorage,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .authenticator(TokenAuthenticator(localStorage, context))
            .addInterceptor(ChuckInterceptor(context))
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("token", localStorage.accessToken)
                    .build()
                it.proceed(request)
            }
            .build()
    }

    class TokenAuthenticator(val localStorage: LocalStorage, val context: Context) : Authenticator {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ChuckInterceptor(context))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        override fun authenticate(route: Route?, response: Response): Request? {
            var request: Request? = null
            val api: AuthApi = retrofit.create(AuthApi::class.java)

            val refreshResponse = api.userRefreshToken(
                localStorage.refreshToken,
                ResetRequest(localStorage.phoneNumberUser)
            ).execute()

            refreshResponse.apply {
                if (isSuccessful) {
                    body()?.data?.let {
                        localStorage.accessToken = it.accessToken
                        localStorage.refreshToken = it.refreshToken

                        request = refreshResponse.raw().request
                            .newBuilder()
                            .removeHeader("refresh_token")
                            .addHeader("refresh_token", it.refreshToken)
                            .build()
                    }
                }
            }
            return request
        }
    }
}
