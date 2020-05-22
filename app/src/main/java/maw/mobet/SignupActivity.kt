package maw.mobet

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import maw.mobet.api.ResultItem
import maw.mobet.api.SignupData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.colorSL
import splitties.resources.txt
import splitties.toast.toast
import java.util.concurrent.TimeUnit

class SignupActivity : AppCompatActivity(), View.OnFocusChangeListener {
    private lateinit var auth: FirebaseAuth
    private var emailOk = 0 // 0: 확인x, 1: 사용가능, 2: 중복
    private var nickOk = 0  // 0: 확인x, 1: 사용가능, 2: 중복
    private var phoneOk = 0 // 0: 확인x, 1: 사용가능, 2: 중복, 3: 인증완료, 4: 코드전송, 5: 잘못된코드
    private var isClickable = true
    private val errorColor by lazy {
        nick_edit_l.errorCurrentTextColors
    }
    private lateinit var storedVerificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        before.visibility = View.VISIBLE
        after.visibility = View.GONE
        code_l.visibility = View.GONE
        errorColor  // 초기화

        // 액션바
        changeTitle(app_title_txt, getString(R.string.signup_title))
        back_img.visibility = View.VISIBLE
        noti_img.visibility = View.GONE
        back_img.setOnClickListener {
            finish()
        }

        // EditText
        email_edit.addTextChangedListener {
            if (emailOk != 0) {
                emailOk = 0
                email_edit_l.error = null
                email_btn.isClickable = true
            }
        }
        nick_edit.addTextChangedListener {
            if (nickOk != 0) {
                nickOk = 0
                nick_edit_l.error = null
                nick_btn.isClickable = true
            }
        }
        phone_edit.addTextChangedListener {
            if (phoneOk != 0) {
                phoneOk = 0
                phone_edit_l.error = null
                phone_btn.isClickable = true
                code_l.visibility = View.GONE
                code_edit_l.error = null
                code_btn.isClickable = true
            }
        }

        email_edit.onFocusChangeListener = this
        passwd_edit.onFocusChangeListener = this
        passwd2_edit.onFocusChangeListener = this
        nick_edit.onFocusChangeListener = this
        phone_edit.onFocusChangeListener = this
        code_edit.onFocusChangeListener = this

        auth = FirebaseAuth.getInstance()
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (p1) {
            return
        }
        when (p0) {
            // 이메일
            email_edit -> {
                email_edit_l.setErrorTextColor(ColorStateList(
                    arrayOf(intArrayOf(0)),
                    intArrayOf(errorColor)
                ))
                if (email_edit.text.toString().isEmpty()) {
                    email_edit_l.error = txt(R.string.not_email)
                } else if (!Regex.email.matches(email_edit.text.toString())) {
                    email_edit_l.error = txt(R.string.mis_email)
                } else if (emailOk == 0) {
                    email_edit_l.error = txt(R.string.not_email_ok)
                } else if (emailOk == 2) {
                    email_edit_l.error = txt(R.string.email_no)
                } else if (emailOk == 1) {
                    email_edit_l.setErrorTextColor(colorSL(R.color.colorControlNormal))
                    email_edit_l.error = txt(R.string.email_ok)
                } else {
                    email_edit_l.error = null
                }
            }
            // 비밀번호
            passwd_edit -> {
                if (passwd_edit.text.toString().isEmpty()) {
                    passwd_edit_l.error = txt(R.string.not_passwd)
                } else if (!passwdCheck(passwd_edit.text.toString())) {
                    passwd_edit_l.error = txt(R.string.wrong_passwd)
                } else {
                    passwd_edit_l.error = null
                }
            }
            passwd2_edit -> {
                if (passwd_edit.text.toString() != passwd2_edit.text.toString()) {
                    passwd2_edit_l.error = txt(R.string.mis_passwd)
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
                    nick_edit_l.error = txt(R.string.not_nick)
                } else if (nickOk == 0) {
                    nick_edit_l.error = txt(R.string.not_nick_ok)
                } else if (nickOk == 2) {
                    nick_edit_l.error = txt(R.string.nick_no)
                } else if (nickOk == 1) {
                    nick_edit_l.setErrorTextColor(colorSL(R.color.colorControlNormal))
                    nick_edit_l.error = txt(R.string.nick_ok)
                } else {
                    nick_edit_l.error = null
                }
            }
            // 전화번호
            phone_edit -> {
                if (phone_edit.text.toString().isEmpty()) {
                    phone_edit_l.error = txt(R.string.not_phone)
                } else if (!Regex.phone.matches(phone_edit.text.toString())) {
                    phone_edit_l.error = txt(R.string.mis_phone)
                } else if (phoneOk == 0) {
                    phone_edit_l.error = txt(R.string.not_phone_ok)
                } else if (phoneOk == 2) {
                    phone_edit_l.error = txt(R.string.phone_no)
                } else if (phoneOk == 4) {
                    phone_edit_l.setErrorTextColor(colorSL(R.color.colorControlNormal))
                    phone_edit_l.error = txt(R.string.send_code)
                } else if (phoneOk == 3) {
                    phone_edit_l.setErrorTextColor(colorSL(R.color.colorControlNormal))
                    phone_edit_l.error = txt(R.string.code_ok)
                } else {
                    phone_edit_l.error = null
                }
            }
            // 인증코드
            code_edit -> {
                if (code_edit.text.toString().isEmpty()) {
                    code_edit_l.error = txt(R.string.not_code)
                } else if (phoneOk == 5) {
                    code_edit_l.error = txt(R.string.not_nick_ok)
                } else {
                    code_edit_l.error = null
                }
            }
        }
    }

    fun onclick(view: View) {
        if (!isClickable) {
            return
        }
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
            // 이메일 중복확인
            email_btn -> {
                val email = email_edit.text.toString()
                if (emailOk != 0) {
                    return
                } else if (email.isEmpty()) {
                    toast(R.string.not_email)
                    return
                }
                email_btn.isClickable = false

                val service = RetrofitClient.getInstance()
                val dataCall = service.emailCheck(email)
                dataCall.enqueue(object : Callback<ResultItem> {
                    override fun onResponse(
                        call: Call<ResultItem>, response: Response<ResultItem>
                    ) {
                        val result = response.body()
                        when (result?.code) {
                            // 사용가능
                            0 -> {
                                emailOk = 1
                                email_edit_l.setErrorTextColor(colorSL(R.color.colorControlNormal))
                                email_edit_l.error = txt(R.string.email_ok)
                            }
                            // 중복
                            1 -> {
                                emailOk = 2
                                email_edit_l.error = txt(R.string.email_no)
                            }
                            // 오류
                            else -> {
                                toast("${txt(R.string.error)} ${result?.code}")
                                email_btn.isClickable = true
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                        toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                        email_btn.isClickable = true
                    }
                })
            }
            // 닉네임 중복확인
            nick_btn -> {
                val nick = nick_edit.text.toString()
                if (nickOk != 0) {
                    return
                } else if (nick.isEmpty()) {
                    toast(R.string.not_nick)
                    return
                }
                nick_btn.isClickable = false

                val service = RetrofitClient.getInstance()
                val dataCall = service.nickCheck(nick)
                dataCall.enqueue(object : Callback<ResultItem> {
                    override fun onResponse(
                        call: Call<ResultItem>, response: Response<ResultItem>
                    ) {
                        val result = response.body()
                        when (result?.code) {
                            // 사용가능
                            0 -> {
                                nickOk = 1
                                nick_edit_l.setErrorTextColor(colorSL(R.color.colorControlNormal))
                                nick_edit_l.error = txt(R.string.nick_ok)
                            }
                            // 중복
                            1 -> {
                                nickOk = 2
                                nick_edit_l.error = txt(R.string.nick_no)
                            }
                            // 오류
                            else -> {
                                toast("${txt(R.string.error)} ${result?.code}")
                                nick_btn.isClickable = true
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                        toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                        nick_btn.isClickable = true
                    }
                })
            }
            // 인증코드 전송
            phone_btn -> {
                val phone = phone_edit.text.toString()
                if (phoneOk != 0) {
                    return
                } else if (phone.isEmpty()) {
                    toast(R.string.not_phone)
                    return
                } else if (!Regex.phone.matches(phone)) {
                    toast(R.string.mis_phone)
                    return
                }
                phone_btn.isClickable = false

                val service = RetrofitClient.getInstance()
                val dataCall = service.phoneCheck(
                    phone.replace("[^0-9]".toRegex(), "")
                )
                dataCall.enqueue(object : Callback<ResultItem> {
                    override fun onResponse(
                        call: Call<ResultItem>, response: Response<ResultItem>
                    ) {
                        val result = response.body()
                        when (result?.code) {
                            // 사용가능
                            0 -> {
                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+82 ${phone.removePrefix("0")}",
                                    60,
                                    TimeUnit.SECONDS,
                                    this@SignupActivity,
                                    callbacks
                                )
                            }
                            // 중복
                            1 -> {
                                phoneOk = 2
                                phone_edit_l.error = txt(R.string.phone_no)
                            }
                            // 오류
                            else -> {
                                toast("${txt(R.string.error)} ${result?.code}")
                                phone_btn.isClickable = true
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                        toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                        phone_btn.isClickable = true
                    }
                })
            }
            // 인증코드 확인
            code_btn -> {
                val code = code_edit.text.toString()
                if (phoneOk != 4 && phoneOk != 5) {
                    return
                } else if (code.isEmpty()) {
                    toast(R.string.not_code)
                    return
                }
                code_btn.isClickable = false

                val credential = PhoneAuthProvider.getCredential(
                    storedVerificationId,
                    code
                )
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            phoneOk = 3
                            phone_edit_l.setErrorTextColor(colorSL(R.color.colorControlNormal))
                            phone_edit_l.error = txt(R.string.code_ok)
                            code_l.visibility = View.GONE
                        } else {
                            phoneOk = 5
                            code_edit_l.error = txt(R.string.not_code_ok)
                            code_btn.isClickable = true
                        }
                        auth.currentUser?.delete()
                        auth.signOut()
                    }
            }
            // 회원가입
            signup_btn -> {
                val data = makeDate() ?: return
                isClickable = false

                // 계정 생성
                auth.createUserWithEmailAndPassword(data.email, passwd_edit.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser!!
                            data.uid = user.uid

                            // 서버로 계정 정보 전송
                            val service = RetrofitClient.getInstance()
                            val dataCall = service.signup(data)
                            dataCall.enqueue(object : Callback<ResultItem> {
                                override fun onResponse(
                                    call: Call<ResultItem>, response: Response<ResultItem>
                                ) {
                                    val result = response.body()
                                    if (result?.code == 0) {
                                        toast(R.string.signup_ok)
                                        finish()
                                        return
                                    }
                                    toast("${txt(R.string.error)} ${result?.code}")
                                    isClickable = true
                                }

                                override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                                    toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                                    isClickable = true
                                }
                            })
                        } else {
                            toast("${txt(R.string.signup_error)}\n${task.exception}")
                            isClickable = true
                        }
                        auth.signOut()
                    }
            }
        }
    }

    private fun makeDate(): SignupData? {
        val email = email_edit.text.toString()
        val passwd = passwd_edit.text.toString()
        val passwd2 = passwd2_edit.text.toString()
        val nick = nick_edit.text.toString()
        val phone = phone_edit.text.toString().replace("[^0-9]".toRegex(), "")

        when {
            email.isEmpty() -> {
                toast(R.string.not_email)
                return null
            }

            !Regex.email.matches(email) -> {
                toast(R.string.mis_email)
                return null
            }

            emailOk != 1 -> {
                toast(R.string.not_email_ok)
                return null
            }

            passwd.isEmpty() -> {
                toast(R.string.not_passwd)
                return null
            }

            !passwdCheck(passwd) -> {
                toast(R.string.wrong_passwd)
                return null
            }

            passwd != passwd2 -> {
                toast(R.string.mis_passwd)
                return null
            }

            nick.isEmpty() -> {
                toast(R.string.not_nick)
                return null
            }

            nickOk != 1 -> {
                toast(R.string.not_nick_ok)
                return null
            }

            phone.isEmpty() -> {
                toast(R.string.not_phone)
                return null
            }

            !Regex.phone.matches(phone) -> {
                toast(R.string.mis_phone)
                return null
            }

            phoneOk != 3 -> {
                toast(R.string.not_phone_ok)
                return null
            }
        }
        return SignupData(email, nick, phone)
    }

    private fun passwdCheck(str: String): Boolean {
        // 알파벳 대문자와 소문자, 특수문자, 숫자. 4가지 종류 중 두 종류 이상의 문자구성과
        // 8자리 이상의 길이로 구성된 문자열
        var type = 0
        if (Regex.alphabetUpper.containsMatchIn(str)) {
            type++
        }
        if (Regex.alphabetLower.containsMatchIn(str)) {
            type++
        }
        if (Regex.number.containsMatchIn(str)) {
            type++
        }
        if (Regex.specialChar.containsMatchIn(str)) {
            type++
        }

        if (type < 2 || str.length < 8) {
            return false
        }
        return true
    }

    // 인증코드 전송 콜백
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        // 인증 완료(자동인증)
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            phoneOk = 3
            phone_edit_l.setErrorTextColor(colorSL(R.color.colorControlNormal))
            phone_edit_l.error = txt(R.string.code_ok)
            code_l.visibility = View.GONE
        }
        // 인증 실패
        override fun onVerificationFailed(e: FirebaseException) {
            toast("${txt(R.string.error)}\n${e.localizedMessage}")
            phone_btn.isClickable = true
        }
        // 코드전송
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            phoneOk = 4
            phone_edit_l.setErrorTextColor(colorSL(R.color.colorControlNormal))
            phone_edit_l.error = txt(R.string.send_code)
            code_l.visibility = View.VISIBLE
            code_edit_l.error = null
            code_btn.isClickable = true
            storedVerificationId = verificationId
        }
    }
}
