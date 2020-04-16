package maw.mobet

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.widget.TextView
import com.google.gson.GsonBuilder
import maw.mobet.api.AppService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object RetrofitClient {
    private val service by lazy {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        Retrofit.Builder()
            .baseUrl("https://ljm.wo.tc/test/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AppService::class.java)
    }

    fun getInstance(): AppService = service
}

fun strToDate(date: String): Date? {
    return SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(date)
}

fun dateToStr(date: Date, format: String): String {
    return SimpleDateFormat(format, Locale.KOREA).format(date)
}

//fun intToWon(num: Int): String {
//    return NumberFormat.getCurrencyInstance(Locale.KOREA).format(num)
//}

fun intToStr(num: Int, prefix: String = "", suffix: String = ""): String {
    return prefix + DecimalFormat("###,###").format(num) + suffix
}

fun dpToPx(context: Context, dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    )
}

fun diffDate(fromDate: Date, toDate: Date): Int {
    val diff = Math.ceil((toDate.time - fromDate.time).toDouble() / 86400000)
    return diff.toInt()
}

fun changeTitle(view: TextView, title: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        view.typeface = view.resources.getFont(R.font.notosanskr_m)
    }
    view.text = title
    view.textSize = 16.3f
    view.letterSpacing = 0.03f
}
