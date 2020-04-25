package maw.mobet

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import maw.mobet.api.NickData
import maw.mobet.api.ResultItem
import maw.mobet.api.SignupData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity(), View.OnFocusChangeListener {
    var nickOk = false
    var codeOk = false
    val errorColor by lazy {
        nick_edit_l.errorCurrentTextColors
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        before.visibility = View.VISIBLE
        after.visibility = View.GONE
        errorColor

        // 액션바
        changeTitle(app_title_txt, getString(R.string.signup_title))
        back_img.visibility = View.VISIBLE
        noti_img.visibility = View.GONE
        back_img.setOnClickListener {
            finish()
        }

        // Spinner
        email_cmb.adapter = ArrayAdapter(
            this,
            R.layout.spinner_item2,
            resources.getStringArray(R.array.email)
        )
        email_cmb.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    email2_edit.isEnabled = true
                } else {
                    email2_edit.isEnabled = false
                    val emailArr = resources.getStringArray(R.array.email)
                    email2_edit.setText(emailArr[p2])
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        // EditText
        nick_edit.addTextChangedListener {
            if (nickOk) {
                nickOk = false
                nick_edit_l.error = null
            }
        }

        email1_edit.onFocusChangeListener = this
        email2_edit.onFocusChangeListener = this
        passwd_edit.onFocusChangeListener = this
        passwd2_edit.onFocusChangeListener = this
        nick_edit.onFocusChangeListener = this
        code_edit.onFocusChangeListener = this
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (p1) {
            return
        }
        when (p0) {
            // 비밀번호
            passwd_edit -> {
                if (passwd_edit.text.toString().isEmpty()) {
                    passwd_edit_l.error = resources.getString(R.string.not_passwd)
                } else {
                    passwd_edit_l.error = null
                }
            }
            passwd2_edit -> {
                if (passwd_edit.text.toString() != passwd2_edit.text.toString()) {
                    passwd2_edit_l.error = resources.getString(R.string.mis_passwd)
                } else {
                    passwd2_edit_l.error = null
                }
            }
            // 닉네임
            nick_edit -> {
                nick_edit_l.setErrorTextColor(ColorStateList(
                    arrayOf(intArrayOf(0)),
                    intArrayOf(errorColor)
                ))
                if (nick_edit.text.toString().isEmpty()) {
                    nick_edit_l.error = resources.getString(R.string.not_nick)
                } else if (!nickOk) {
                    nick_edit_l.error = resources.getString(R.string.not_nick_ok)
                } else if (nickOk) {
                    nick_edit_l.setErrorTextColor(getColorStateList(R.color.colorControlNormal))
                    nick_edit_l.error = resources.getString(R.string.nick_ok)
                } else {
                    nick_edit_l.error = null
                }
            }
        }
    }

    fun onclick(view: View) {
        when (view) {
            // 회원가입 전 다음 버튼
            next_btn -> {
                val outAnim = AnimationUtils.loadAnimation(this, R.anim.slide_out_top)
                outAnim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        before.visibility = View.GONE
                    }

                    override fun onAnimationStart(p0: Animation?) {
                        next_btn.isClickable = false
                    }
                })
                before.startAnimation(outAnim)

                val inAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
                inAnim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                    }

                    override fun onAnimationStart(p0: Animation?) {
                        after.visibility = View.VISIBLE
                    }
                })
                after.startAnimation(inAnim)
            }
            // 닉네임 중복확인
            nick_btn -> {
                if (nick_edit.text.toString().isEmpty()) {
                    toast(resources.getString(R.string.not_nick))
                    return
                }

                val service = RetrofitClient.getInstance()
                val dataCall = service.nickCheck(NickData(nick_edit.text.toString()))
                dataCall.enqueue(object : Callback<ResultItem> {
                    override fun onResponse(
                        call: Call<ResultItem>, response: Response<ResultItem>
                    ) {
                        val result = response.body()
                        if (result?.code == 0) {
                            nick_edit_l.setErrorTextColor(
                                getColorStateList(R.color.colorControlNormal)
                            )
                            nick_edit_l.error = resources.getString(R.string.nick_ok)
                            nickOk = true
                            return
                        }
                        Toast.makeText(
                            this@SignupActivity,
                            resources.getString(R.string.error) + "\n" +
                                    result?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                        Toast.makeText(
                            this@SignupActivity,
                            resources.getString(R.string.network_error) + "\n" +
                                    t.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
            // 인증코드 확인
            code_btn -> {
                // 일단 통과
                codeOk = true
            }
            // 회원가입
            signup_btn -> {
                val service = RetrofitClient.getInstance()
                val data = makeDate() ?: return
                val dataCall = service.signup(data)
                dataCall.enqueue(object : Callback<ResultItem> {
                    override fun onResponse(
                        call: Call<ResultItem>, response: Response<ResultItem>
                    ) {
                        val result = response.body()
                        if (result?.code == 0) {
                            Toast.makeText(
                                this@SignupActivity,
                                resources.getString(R.string.signup_ok),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                            return
                        }
                        Toast.makeText(
                            this@SignupActivity,
                            resources.getString(R.string.error) + "\n" +
                                    result?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                        Toast.makeText(
                            this@SignupActivity,
                            resources.getString(R.string.network_error) + "\n" +
                                    t.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }
    }

    private fun makeDate(): SignupData? {
        val email = if (email1_edit.text.toString().isEmpty() || email2_edit.text.toString().isEmpty()) {
            null
        } else {
            "${email1_edit.text.toString()}@${email2_edit.text.toString()}"
        }
        val passwd = passwd_edit.text.toString()
        val nick = nick_edit.text.toString()

        when {
            email == null -> {
                toast(resources.getString(R.string.not_email))
                return null
            }

            !Regex.email.matches(email) -> {
                toast(resources.getString(R.string.mis_email))
                return null
            }

            passwd_edit.text.toString().isEmpty() -> {
                toast(resources.getString(R.string.not_passwd))
                return null
            }

            passwd_edit.text.toString() != passwd2_edit.text.toString() -> {
                toast(resources.getString(R.string.mis_passwd))
                return null
            }

            nick_edit.text.toString().isEmpty() -> {
                toast(resources.getString(R.string.not_nick))
                return null
            }

            !nickOk -> {
                toast(resources.getString(R.string.not_nick_ok))
                return null
            }

            !codeOk -> {
                toast(resources.getString(R.string.not_code))
                return null
            }
        }
        return SignupData(email!!, passwd, nick)
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
