package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import splitties.activities.start
import splitties.toast.toast

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        app_content.visibility = View.GONE

        app_content.post {
            app_content.visibility = View.VISIBLE
        }

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        if (user != null) {
            // 자동로그인
            RetrofitClient.setKey(user.uid)
            start<MainActivity>()
            finish()
        }
    }

    fun onclick(view: View) {
        when (view) {
            // 로그인
            login_btn -> {
                when {
                    email_edit.text.toString().isEmpty() -> {
                        toast(R.string.not_email)
                        return
                    }

                    passwd_edit.text.toString().isEmpty() -> {
                        toast(R.string.not_passwd)
                        return
                    }
                }

                login_btn.isClickable = false
                // 로그인 처리
                auth.signInWithEmailAndPassword(
                    email_edit.text.toString(),
                    passwd_edit.text.toString()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        onStart()
                    } else {
                        toast(R.string.login_error)
                        login_btn.isClickable = true
                    }
                }
            }
            // 회원가입
            signup_txt -> {
                start<SignupActivity>()
            }
        }
    }
}
