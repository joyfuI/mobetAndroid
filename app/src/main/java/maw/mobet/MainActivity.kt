package maw.mobet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import maw.mobet.api.ResultItem
import maw.mobet.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.activities.start
import splitties.resources.txt
import splitties.toast.toast

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 액션바
        noti_img.setOnClickListener {
            start<NotifyActivity>()
        }

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_home) {
                fab.visibility = View.VISIBLE
            } else {
                fab.visibility = View.GONE
            }
        }
        nav_view.setupWithNavController(navController)

//        fab.alpha = 0.5f

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            toast(R.string.access_error)
            finish()
            return
        }

        val service = RetrofitClient.getInstance()
        val dataCall = service.notify()
        dataCall.enqueue(object : Callback<ResultItem> {
            override fun onResponse(call: Call<ResultItem>, response: Response<ResultItem>) {
                val result = response.body()
                noti_img.background = if (result?.code == 0) {
                    // 없음
                    getDrawable(R.drawable.chat)
                } else {
                    // 있음
                    getDrawable(R.drawable.chat_new)
                }
            }

            override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }

    fun onClick(view: View) {
        when (view) {
            // 플러스 버튼
            fab -> {
                val intent = Intent(this, CreategameActivity::class.java)
                startActivityForResult(intent, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            // 경쟁전 생성
            if (resultCode == RESULT_OK) {
                val refresh = data!!.getBooleanExtra("result", false)
                if (refresh) {
                    val home = navHostFragment.childFragmentManager.fragments[0] as HomeFragment
                    home.refresh()
                }
            }
        }
    }
}
