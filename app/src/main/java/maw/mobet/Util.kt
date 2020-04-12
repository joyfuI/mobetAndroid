package maw.mobet

import android.content.Context
import android.util.TypedValue
import com.google.gson.GsonBuilder
import maw.mobet.api.AppService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat
import java.text.NumberFormat
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

fun intToWon(num: Int): String {
    return NumberFormat.getCurrencyInstance(Locale.KOREA).format(num)
}

fun intToStr(num: Int, suffix: String = ""): String {
    return DecimalFormat("###,###").format(num) + suffix
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
