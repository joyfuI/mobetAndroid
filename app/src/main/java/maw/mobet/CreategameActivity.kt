package maw.mobet

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_creategame.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import maw.mobet.api.CreategameData
import maw.mobet.api.ResultItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

        val cal = Calendar.getInstance()
        cal.time = today
        cal.add (Calendar.DAY_OF_MONTH, 3)
        start_edit.setText(dateToStr(cal.time, "yyyy-MM-dd"))
        start_edit.isFocusable = false
        end_edit.setText(dateToStr(cal.time, "yyyy-MM-dd"))
        end_edit.isFocusable = false

        category_img.tag = -1

        title_edit.onFocusChangeListener = this
        greater_edit.onFocusChangeListener = this
        less_edit.onFocusChangeListener = this
        price_edit.onFocusChangeListener = this
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (p1) {
            return
        }
        when (p0) {
            // 제목
            title_edit -> {
                if (title_edit.text.toString().isEmpty()) {
                    title_edit.hint = null
                    title_edit_l.error = resources.getString(R.string.creategame_title_hint)
                } else {
                    title_edit.hint = resources.getString(R.string.creategame_title_hint)
                    title_edit_l.error = null
                }
            }
            // 참가조건 이상
            greater_edit -> {
                greater_edit_l.error = null
                try {
                    if (greater_edit.text.toString().isNotEmpty()) {
                        Integer.parseInt(greater_edit.text.toString())
                    }
                } catch (e: NumberFormatException) {
                    greater_edit_l.error = resources.getString(R.string.not_number)
                }
            }
            // 참가조건 이하
            less_edit -> {
                less_edit_l.error = null
                try {
                    if (less_edit.text.toString().isNotEmpty()) {
                        Integer.parseInt(less_edit.text.toString())
                    }
                } catch (e: NumberFormatException) {
                    less_edit_l.error = resources.getString(R.string.not_number)
                }
            }
            // 금액
            price_edit -> {
                price_edit_l.error = null
                try {
                    if (price_edit.text.toString().isEmpty()) {
                        price_edit.hint = null
                        price_edit_l.error = resources.getString(R.string.creategame_price_hint)
                    } else {
                        title_edit.hint = resources.getString(R.string.creategame_price_hint)
                        Integer.parseInt(price_edit.text.toString())
                    }
                } catch (e: NumberFormatException) {
                    price_edit_l.error = resources.getString(R.string.not_number)
                }
            }
        }
    }

    fun onClick(view: View) {
        when (view) {
            // 시작날짜, 종료날짜
            start_edit, end_edit -> {
                val edit = view as TextInputEditText
                val editL = if (view == start_edit) start_edit_l else end_edit_l

                val date = strToDate(edit.text.toString())!!
                val cal = Calendar.getInstance()
                cal.time = date
                DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
                        cal.set(i, i2, i3)
                        edit.setText(dateToStr(cal.time, "yyyy-MM-dd"))

                        if (diffDate(today, cal.time) < 1) {
                            editL.error = resources.getString(R.string.todate_less_date)
                        } else {
                            editL.error = null
                        }
                        if (
                            diffDate(
                                strToDate(start_edit.text.toString())!!,
                                strToDate(end_edit.text.toString())!!
                            ) < 0
                        ) {
                            end_edit_l.error = resources.getString(R.string.enddate_less_date)
                        } else {
                            end_edit_l.error = null
                        }
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            // 카테고리 선택하기
            category_img -> {
                val intent = Intent(this, CategoryActivity::class.java)
                startActivityForResult(intent, 0)
            }
            // 생성
            create -> {
                val service = RetrofitClient.getInstance()
                val data = makeDate() ?: return
                val dataCall = service.createGame(data)
                dataCall.enqueue(object : Callback<ResultItem> {
                    override fun onResponse(
                        call: Call<ResultItem>, response: Response<ResultItem>
                    ) {
                        val result = response.body()
                        if (result?.success == true) {
                            finish()
                            return
                        }
                        Toast.makeText(
                            this@CreategameActivity,
                            resources.getString(R.string.error) + "\n" +
                                    result?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                        Toast.makeText(
                            this@CreategameActivity,
                            resources.getString(R.string.network_error) + "\n" +
                                    t.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
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

    private fun makeDate(): CreategameData? {
        val title = title_edit.text.toString()
        val public = pub_rdo.isChecked
        val greater = try {
            Integer.parseInt(greater_edit.text.toString())
        } catch (e: NumberFormatException) {
            null
        }
        val less = try {
            Integer.parseInt(less_edit.text.toString())
        } catch (e: NumberFormatException) {
            null
        }
        val start = strToDate(start_edit.text.toString())!!
        val end = strToDate(end_edit.text.toString())!!
        val price = try {
            val num = Integer.parseInt(price_edit.text.toString())
            if (price_cmb.selectedItemId == 0L) -num else num
        } catch (e: NumberFormatException) {
            null
        }
        val category = category_img.tag as Int

        when {
            title.isEmpty() -> {
                toast(resources.getString(R.string.creategame_title_hint))
                return null
            }

            greater_edit_l.error != null -> {
                toast(greater_edit_l.error.toString())
                return null
            }

            less_edit_l.error != null -> {
                toast(less_edit_l.error.toString())
                return null
            }

            start_edit_l.error != null -> {
                toast(start_edit_l.error.toString())
                return null
            }

            end_edit.error != null -> {
                toast(end_edit.error.toString())
                return null
            }

            price_edit_l.error != null -> {
                toast(price_edit_l.error.toString())
                return null
            }

            price == null -> {
                toast(resources.getString(R.string.creategame_price_hint))
                return null
            }
        }
        return CreategameData(title, public, greater, less, start, end, price!!, category)
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}