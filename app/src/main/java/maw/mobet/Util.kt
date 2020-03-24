package maw.mobet

import android.content.Context
import android.util.TypedValue
import java.text.NumberFormat
import java.util.*

fun intToWon(num: Int): String {
    return NumberFormat.getCurrencyInstance(Locale.KOREA).format(num)
}

fun dpToPx(context: Context, dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
}
