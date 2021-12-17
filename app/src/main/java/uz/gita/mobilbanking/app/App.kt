package uz.gita.mobilbanking.app

import android.app.Application
import android.content.Context
import nl.qbusict.cupboard.BuildConfig
import timber.log.Timber
import uz.gita.mobilbanking.data.local.MyPref

class App : Application() {
    companion object {
        lateinit var instance: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        MyPref.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}