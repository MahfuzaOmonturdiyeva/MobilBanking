package uz.gita.mobilbanking.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import nl.qbusict.cupboard.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var instance: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}