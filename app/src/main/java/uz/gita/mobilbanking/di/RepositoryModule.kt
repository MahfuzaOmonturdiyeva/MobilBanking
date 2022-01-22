package uz.gita.mobilbanking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.mobilbanking.data.source.remote.api.api.TransferApi
import uz.gita.mobilbanking.domain.repository.AuthRepository
import uz.gita.mobilbanking.domain.repository.CardRepository
import uz.gita.mobilbanking.domain.repository.ProfileRepository
import uz.gita.mobilbanking.domain.repository.TransferRepository
import uz.gita.mobilbanking.domain.repository.impl.AuthRepositoryImpl
import uz.gita.mobilbanking.domain.repository.impl.CardRepositoryImpl
import uz.gita.mobilbanking.domain.repository.impl.ProfileRepositoryImpl
import uz.gita.mobilbanking.domain.repository.impl.TransferRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    // @Named ()
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun provideCardRepository(impl: CardRepositoryImpl): CardRepository

    @Binds
    fun provideTransferRepository(impl: TransferRepositoryImpl): TransferRepository

}