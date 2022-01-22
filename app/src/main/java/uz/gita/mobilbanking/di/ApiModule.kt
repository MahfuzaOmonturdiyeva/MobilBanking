package uz.gita.mobilbanking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.gita.mobilbanking.data.source.remote.api.api.AuthApi
import uz.gita.mobilbanking.data.source.remote.api.api.CardApi
import uz.gita.mobilbanking.data.source.remote.api.api.ProfileApi
import uz.gita.mobilbanking.data.source.remote.api.api.TransferApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @[Provides Singleton]
    fun getAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @[Provides Singleton]
    fun getProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

    @[Provides Singleton]
    fun getCardApi(retrofit: Retrofit): CardApi = retrofit.create(CardApi::class.java)

    @[Provides Singleton]
    fun getTransferApi(retrofit: Retrofit): TransferApi = retrofit.create(TransferApi::class.java)
}