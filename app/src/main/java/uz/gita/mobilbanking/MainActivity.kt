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

//    override fun onBackPressed() {
//        if (isClickBackPressed) {
//            finishAffinity()
//            exitProcess(0)
//        } else {
//            showToast("Please click back again to exit")
//            isClickBackPressed = true
//        }
//
//        val runnable = Runnable {
//            isClickBackPressed = false
//        }
//
//        Handler(Looper.getMainLooper()).postDelayed(runnable, 2000)
//    }

    //
//    binding.bottomNavigationFrMain.setOnItemSelectedListener {
//        when (it.itemId) {
//            R.id.action_home -> {
////                     Toast.makeText(this, "home", Toast.LENGTH_SHORT).show()
//                presenterImpl.onReloadView(false)
//                isFavorite = false
//                //   binding.textNotFound.visibility = View.INVISIBLE
//            }
//            R.id.action_favourite -> {
////                    Toast.makeText(this, "fav", Toast.LENGTH_SHORT).show()
//                presenterImpl.onReloadView(true)
//                // binding.textNotFound.visibility = View.INVISIBLE
//                isFavorite = true
//            }
//        }
//        true
//    }
}