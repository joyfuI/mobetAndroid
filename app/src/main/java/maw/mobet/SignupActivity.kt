package maw.mobet

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.custom_actionbar.*

class SignupActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        before.visibility = View.VISIBLE
        after.visibility = View.GONE

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
        email_cmb.onItemSelectedListener = this
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
        }
    }

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
