package maw.mobet

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // xml을 사용하지 않고 그리기
        val layout = LinearLayout(this)
        val LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.setLayoutParams(LayoutParams)
        layout.gravity = Gravity.CENTER
        val textView = TextView(this)
        textView.text = getString(R.string.app_name)
        textView.textSize = 22f
        textView.setTextColor(getColor(R.color.colorPrimary))
        textView.letterSpacing = 0.15f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.typeface = resources.getFont(R.font.netmarble_b)
        }
        layout.addView(textView)
        setContentView(layout)

        // 초기화 영역
        RetrofitClient.getInstance()

        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }
}
