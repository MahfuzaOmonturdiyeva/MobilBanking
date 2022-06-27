package uz.gita.mobilbanking.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Handler
import android.os.IBinder
import android.os.SystemClock
import timber.log.Timber
import uz.gita.mobilbanking.MainActivity
import java.lang.UnsupportedOperationException

class ConnectionService: Service(){
    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
    }

    override fun onBind(p0: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implement")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    fun isOnline(context:Context):Boolean{
        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkInfo=connectivityManager.activeNetworkInfo

        return netWorkInfo!=null && netWorkInfo.isConnectedOrConnecting
    }

    var handler = Handler()
    private val periodicUpdate: Runnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, 1 * 1000 - SystemClock.elapsedRealtime() % 1000)
            val broadCastIntent = Intent()
            broadCastIntent.action = MainActivity.BroadcastStringForAction
            broadCastIntent.putExtra("online_status", "" + isOnline(this@ConnectionService))
            sendBroadcast(broadCastIntent)
        }
    }
}