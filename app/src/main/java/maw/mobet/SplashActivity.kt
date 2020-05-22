package maw.mobet

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import splitties.activities.start
import splitties.dimensions.dip
import splitties.resources.color

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // xml을 사용하지 않고 그리기
        val layout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
        }
        val imageView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(dip(72), dip(59)).apply {
                bottomMargin = dip(8)
            }
            setImageResource(R.drawable.ic_mobet)
        }
        val textView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typeface = resources.getFont(R.font.netmarble_b)
            }
            text = getString(R.string.app_name)
            textSize = 22f
            setTextColor(color(R.color.colorPrimary))
            letterSpacing = 0.15f
        }

        layout.addView(imageView)
        layout.addView(textView)
        setContentView(layout)

        // 초기화 영역
        RetrofitClient.getInstance()

        start<LoginActivity> {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
    }
}
