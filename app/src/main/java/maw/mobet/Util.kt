package maw.mobet

import android.os.Build
import android.widget.TextView
import com.google.gson.GsonBuilder
import maw.mobet.api.AppService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

object RetrofitClient {
    private var key: String? = null

    private val service by lazy {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        Retrofit.Builder()
            .baseUrl("https://ljm.wo.tc/test/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(interceptor)
            .build()
            .create(AppService::class.java)
    }
    // 요청할 때마다 Authorization 정보 추가
    private val interceptor by lazy {
        OkHttpClient.Builder().addInterceptor {
            val request = if (key != null) {
                it.request().newBuilder()
                    .addHeader("Authorization", "Bearer $key")
                    .build()
            } else {
                it.request()
            }
            it.proceed(request)
        }.build()
    }

    fun getInstance(): AppService = service

    fun setKey(value: String) {
        key = value
    }
}

object Regex {
    val email = """^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"""
        .toRegex()
    val alphabetUpper = """[A-Z]""".toRegex()
    val alphabetLower = """[a-z]""".toRegex()
    val number = """\d""".toRegex()
    val specialChar = """[`~!@#$%^&*()\-_=+\\|\[{\]};:'",<.>/?]""".toRegex()
    val phone = """^010-?\d{3,4}-?\d{4}$""".toRegex()
}

fun String.toDate(): Date? {
    return SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(this)
}

fun Date.toString(format: String): String {
    return SimpleDateFormat(format, Locale.KOREA).format(this)
}

//fun intToWon(num: Int): String {
//    return NumberFormat.getCurrencyInstance(Locale.KOREA).format(num)
//}

fun intToStr(num: Int, prefix: String = "", suffix: String = ""): String {
    return prefix + DecimalFormat("###,###").format(num) + suffix
}

fun diffDate(fromDate: Date, toDate: Date): Int {
    val diff = ceil((toDate.time - fromDate.time).toDouble() / 86400000)
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
