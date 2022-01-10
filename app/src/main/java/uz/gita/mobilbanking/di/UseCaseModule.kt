package uz.gita.mobilbanking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.mobilbanking.domain.usecase.auth.*
import uz.gita.mobilbanking.domain.usecase.auth.impl.*
import uz.gita.mobilbanking.domain.usecase.main.MainUseCase
import uz.gita.mobilbanking.domain.usecase.main.impl.MainUseCaseImpl
import uz.gita.mobilbanking.domain.usecase.onCreated.PinUseCase
import uz.gita.mobilbanking.domain.usecase.onCreated.SplashUseCase
import uz.gita.mobilbanking.domain.usecase.onCreated.impl.PinUseCaseImpl
import uz.gita.mobilbanking.domain.usecase.onCreated.impl.SplashUseCaseImpl
import uz.gita.mobilbanking.domain.usecase.setting.PersonalUseCase
import uz.gita.mobilbanking.domain.usecase.setting.SettingsUseCase
import uz.gita.mobilbanking.domain.usecase.setting.impl.PersonalUseCaseImpl
import uz.gita.mobilbanking.domain.usecase.setting.impl.SettingsUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun provideSplashUseCase(impl: SplashUseCaseImpl):
            SplashUseCase

    @Binds
    fun providePinUsaCase(impl: PinUseCaseImpl):
            PinUseCase

    @Binds
    fun provideConfirmPinUsaCase(impl: ConFirmPinUseCaseImpl):
            ConFirmPinUseCase
    @Binds
    fun provideLoginUseCase(impl: LoginUseCaseImpl):
            LoginUseCase

    @Binds
    fun provideRegisterUseCase(impl: RegisterUseCaseImpl):
            RegisterUseCase

    @Binds
    fun provideResetUsaCase(impl: ResetUseCaseImpl):
            ResetUseCase

    @Binds
    fun provideVerifyUsaCase(impl: VerifyUseCaseImpl):
            VerifyUseCase

    @Binds
    fun providePersonalUsaCase(impl: PersonalUseCaseImpl):
            PersonalUseCase

    @Binds
    fun provideSettingUsaCase(impl: SettingsUseCaseImpl):
            SettingsUseCase

    @Binds
    fun provideMainUsaCase(impl: MainUseCaseImpl):
            MainUseCase
}