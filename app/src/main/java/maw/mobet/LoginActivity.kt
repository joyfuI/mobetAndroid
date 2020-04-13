package maw.mobet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        app_content.post {
            app_content.visibility = View.VISIBLE
        }
    }

    fun onclick(view: View) {
        when (view) {
            // 로그인
            login_btn -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            // 회원가입
            signup_txt -> {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
