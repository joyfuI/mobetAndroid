package maw.mobet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import maw.mobet.api.LoginData
import maw.mobet.api.LoginItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            return
                        }
                        Toast.makeText(
                            this@LoginActivity,
                            resources.getString(R.string.error) + "\n" +
                                    result?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(call: Call<LoginItem>, t: Throwable) {
                        Toast.makeText(
                            this@LoginActivity,
                            resources.getString(R.string.network_error) + "\n" +
                                    t.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
            // 회원가입
            signup_txt -> {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
