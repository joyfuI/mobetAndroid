package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import maw.mobet.api.ResultItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.activities.start
import splitties.resources.txt
import splitties.toast.toast

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var isClickable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (intent.getBooleanExtra("anim", true)) {
            app_content.visibility = View.GONE
            app_content.post {
                app_content.visibility = View.VISIBLE
            }
        }

        auth = FirebaseAuth.getInstance()
    }

    fun onclick(view: View) {
        if (!isClickable) {
            return
        }
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
                isClickable = false

                auth.signInWithEmailAndPassword(
                    email_edit.text.toString(),
                    passwd_edit.text.toString()
                ).addOnCompleteListener(this@LoginActivity) { task ->
                    if (task.isSuccessful) {
                        // 로그인 처리
                        RetrofitClient.setKey(auth.currentUser!!.uid)
                        // 로그인 요청
                        val service = RetrofitClient.getInstance()
                        val dataCall = service.login()
                        dataCall.enqueue(object : Callback<ResultItem> {
                            override fun onResponse(
                                call: Call<ResultItem>, response: Response<ResultItem>
                            ) {
                                val result = response.body()
                                if (result?.code != 0) {
                                    User.id = result?.code
                                    start<MainActivity>()
                                    finish()
                                    return
                                }
                                toast(R.string.login_error)
                                auth.signOut()
                                isClickable = true
                            }

                            override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                                toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                                auth.signOut()
                                isClickable = true
                            }
                        })
                    } else {
                        toast(R.string.login_error)
                        isClickable = true
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
