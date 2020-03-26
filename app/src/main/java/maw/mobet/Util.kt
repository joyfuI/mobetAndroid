package maw.mobet

import android.content.Context
import android.util.TypedValue
import maw.mobet.api.AppService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.*

object RetrofitClient {
    private val service by lazy {
        Retrofit.Builder()
            .baseUrl("https://ljm.wo.tc/test/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppService::class.java)
    }

    fun getInstance(): AppService = service
}

fun intToWon(num: Int): String {
    return NumberFormat.getCurrencyInstance(Locale.KOREA).format(num)
}

fun dpToPx(context: Context, dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
}
