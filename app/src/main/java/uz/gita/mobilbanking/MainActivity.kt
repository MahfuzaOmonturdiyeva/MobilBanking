package uz.gita.mobilbanking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.mobilbanking.utils.showToast
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var host: NavHostFragment
    private var isClickBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navhost = findViewById<FragmentContainerView>(R.id.navHost)
        navhost.visibility = View.VISIBLE
        host = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val graph = host.navController.navInflater.inflate(R.navigation.app_navigation)
        host.navController.graph = graph

    }
}