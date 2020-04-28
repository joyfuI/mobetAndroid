package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import splitties.activities.start

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 액션바
        noti_img.setOnClickListener {
            start<NotifyActivity>()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_home) {
                fab.visibility = View.VISIBLE
            } else {
                fab.visibility = View.GONE
            }
        }
        nav_view.setupWithNavController(navController)

        fab.alpha = 0.5f
    }

    fun onClick(view: View) {
        when (view) {
            // 플러스 버튼
            fab -> {
                start<CreategameActivity>()
            }
        }
    }
}
