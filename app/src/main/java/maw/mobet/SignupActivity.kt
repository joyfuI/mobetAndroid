package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.custom_actionbar.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // 액션바
        changeTitle(app_title_txt, getString(R.string.signup_title))
        back_img.visibility = View.VISIBLE
        noti_img.visibility = View.GONE
        back_img.setOnClickListener {
            finish()
        }
    }
}
