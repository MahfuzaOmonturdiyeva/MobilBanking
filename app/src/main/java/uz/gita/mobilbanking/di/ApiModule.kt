package uz.gita.mobilbanking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.gita.mobilbanking.data.source.remote.api.AuthApi
import uz.gita.mobilbanking.data.source.remote.api.ProfileApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @[Provides Singleton]
    fun getAuthApi(retrofit: Retrofit):AuthApi=retrofit.create(AuthApi::class.java)

    @[Provides Singleton]
    fun getProfileApi(retrofit: Retrofit):ProfileApi=retrofit.create(ProfileApi::class.java)

}