package uz.gita.mobilbanking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.*
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.service.ConnectionService
import uz.gita.mobilbanking.ui.screen.OfflineScreen

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var host: NavHostFragment
    private var intentFilter: IntentFilter? = null

    companion object {
        const val BroadcastStringForAction = "checkinternet"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main)
        val navhost = findViewById<FragmentContainerView>(R.id.navHost)
        navhost.visibility = View.VISIBLE
        host = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val graph = host.navController.navInflater.inflate(R.navigation.app_navigation)
        host.navController.graph = graph

        intentFilter = IntentFilter()
        intentFilter!!.addAction(BroadcastStringForAction)
        val serviceIntent = Intent(this, ConnectionService::class.java)
        startService(serviceIntent)

        if (isOnline(this)) {
            setVisibilityON()
        } else setVisibilityOFF()
    }

    public val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (intent.action.equals(BroadcastStringForAction)) {
                if (intent.getStringExtra("online_status").equals("true")) {
                    setVisibilityON()
                } else {
                    setVisibilityOFF()
                }
            }
        }
    }

    public fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkInfo = connectivityManager.activeNetworkInfo

        return netWorkInfo != null && netWorkInfo.isConnectedOrConnecting
    }

    public fun setVisibilityOFF() {
        supportFragmentManager.commit {
            replace<OfflineScreen>(R.id.navHost)
            setReorderingAllowed(true)
            addToBackStack("OfflineScreen")
        }
    }

    public fun setVisibilityON() {
        supportFragmentManager.popBackStack(
            "OfflineScreen",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onRestart() {
        super.onRestart()
        intentFilter?.let {
            registerReceiver(myReceiver, it)
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(myReceiver)
    }

    override fun onResume() {
        super.onResume()
        intentFilter?.let {
            registerReceiver(myReceiver, it)
        }
    }
}