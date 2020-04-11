package maw.mobet

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_creategame.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import java.text.SimpleDateFormat
import java.util.*

class CreategameActivity : AppCompatActivity(), View.OnFocusChangeListener {
    private val today = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creategame)

        noti_img.visibility = View.GONE

        val items = listOf(
            resources.getString(R.string.creategame_price_less),
            resources.getString(R.string.creategame_price_greater)
        )
        price_cmb.adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            items
        )

        start_edit.setText(dateToStr(today, "yyyy-MM-dd"))
        end_edit.setText(dateToStr(today, "yyyy-MM-dd"))
        // DatePickerDialog가 api 24부터 지원
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            start_edit.onFocusChangeListener = this
            end_edit.onFocusChangeListener = this
        }

        category_img.tag = -1
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (p0 == null || !p1) {
            return
        }
        // 소프트키보드 내리기
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(p0.windowToken, 0)
        p0.clearFocus()

        when (p0) {
            // 시작날짜
            start_edit -> {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                    .parse(start_edit.text.toString()) ?: today
                val cal = Calendar.getInstance()
                cal.time = date
                DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
                        cal.set(i, i2, i3)
                        start_edit.setText(dateToStr(cal.time, "yyyy-MM-dd"))
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            // 시작날짜
            end_edit -> {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                    .parse(end_edit.text.toString()) ?: today
                val cal = Calendar.getInstance()
                cal.time = date
                DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
                        cal.set(i, i2, i3)
                        end_edit.setText(dateToStr(cal.time, "yyyy-MM-dd"))
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    fun onClick(view: View) {
        when (view) {
            // 카테고리 선택하기
            category_img -> {
                val intent = Intent(this, CategoryActivity::class.java)
                startActivityForResult(intent, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            // 카테고리 선택
            if (resultCode == RESULT_OK) {
                val drawableArr = resources.obtainTypedArray(R.array.category_drawable)
                val titleArr = resources.getStringArray(R.array.category)
                val position = data!!.getIntExtra("result", -1)

//                category_txt.visibility = View.GONE
                category_txt.text = titleArr[position]
                category_img.tag = position
                category_img.setImageResource(drawableArr.getResourceId(position, -1))

                drawableArr.recycle()
            }
        }
    }
}
