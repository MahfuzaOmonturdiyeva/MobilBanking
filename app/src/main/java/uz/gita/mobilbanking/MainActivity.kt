package uz.gita.mobilbanking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.LottieAnimationView
import kotlin.system.exitProcess

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
//        val lottie = findViewById<LottieAnimationView>(R.id.lottie)

//        lottie.animate().translationY(2000F).setDuration(500).startDelay = 2900
//        Handler(Looper.getMainLooper()).postDelayed({
//            lottie.visibility = View.GONE
//
//        }, 4000)
    }

    override fun onBackPressed() {
        if (isClickBackPressed) {
            finishAffinity()
            exitProcess(0)
        } else {
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show()
            isClickBackPressed = true
        }

        val runnable = Runnable {
            isClickBackPressed = false
        }

        Handler(Looper.getMainLooper()).postDelayed(runnable, 2000)
    }
}