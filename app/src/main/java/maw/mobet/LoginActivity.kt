package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import maw.mobet.api.LoginData
import maw.mobet.api.LoginItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.activities.start
import splitties.resources.txt
import splitties.toast.toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        app_content.visibility = View.GONE

        app_content.post {
            app_content.visibility = View.VISIBLE
        }
    }

    fun onclick(view: View) {
        when (view) {
            // 로그인
            login_btn -> {
                login_btn.isClickable = false

                val service = RetrofitClient.getInstance()
                val dataCall = service.login(
                    LoginData(email_edit.toString(), passwd_edit.toString())
                )
                dataCall.enqueue(object : Callback<LoginItem> {
                    override fun onResponse(
                        call: Call<LoginItem>, response: Response<LoginItem>
                    ) {
                        val result = response.body()
                        if (result?.code == 0) {
                            // 로그인 성공
                            RetrofitClient.setKey(result.key)
                            start<MainActivity>()
                            finish()
                            return
                        }
                        toast("${txt(R.string.error)}\n${result?.message}")
                        login_btn.isClickable = true
                    }

                    override fun onFailure(call: Call<LoginItem>, t: Throwable) {
                        toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                        login_btn.isClickable = true
                    }
                })
            }
            // 회원가입
            signup_txt -> {
                start<SignupActivity>()
            }
        }
    }
}
