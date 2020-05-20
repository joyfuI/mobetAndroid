package maw.mobet

import android.graphics.drawable.GradientDrawable
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import splitties.resources.appColor
import splitties.resources.appColorSL
import splitties.resources.appStr
import splitties.resources.appStrArray
import java.util.*
import kotlin.math.absoluteValue

@BindingAdapter("bind_greater", "bind_less")
fun bindCondition(view: TextView, greater: Int?, less: Int?) {
    if (greater == null || less == null) {
        view.text = appStr(R.string.game_no_condition)
        return
    }
    val str = intToStr(greater, prefix = appStr(R.string.won_char)) +
            " ~ " +
            intToStr(less, prefix = appStr(R.string.won_char))
    view.text = str
}

@BindingAdapter("bind_price")
fun bindPrice(view: TextView, price: Int?) {
    if (price == null) {
        return
    }
    view.text = if (price > 0) {
        intToStr(
            price.absoluteValue,
            prefix = appStr(R.string.won_char),
            suffix = " " + appStr(R.string.greater)
        )
    } else {
        intToStr(
            price.absoluteValue,
            prefix = appStr(R.string.won_char),
            suffix = " " + appStr(R.string.less)
        )
    }
}

@BindingAdapter("bind_price2")
fun bindPrice2(view: TextView, price: Int?) {
    if (price == null) {
        return
    }
    view.text = intToStr(price.absoluteValue, prefix = appStr(R.string.won_char))
}

@BindingAdapter("bind_price2")
fun bindPrice2(view: ImageView, price: Int?) {
    if (price == null) {
        return
    }
    view.setImageResource(if (price > 0) {
        R.drawable.ic_arrow_upward_24dp
    } else {
        R.drawable.ic_arrow_downward_24dp
    })
}

@BindingAdapter("bind_category")
fun bindCategory(view: TextView, category: Int?) {
    if (category == null) {
        return
    }
    val categoryArr = appStrArray(R.array.category)
    view.text = categoryArr[category]
}

@BindingAdapter("bind_category")
fun bindCategory(view: ImageView, category: Int?) {
    if (category == null) {
        return
    }
    val backgroundArr = view.resources.obtainTypedArray(R.array.category_background)
    view.setImageResource(backgroundArr.getResourceId(category, 0))
    backgroundArr.recycle()
}

@BindingAdapter("bind_imgUrl")
fun bindImgUrl(view: CircleImageView, imgUrl: String?) {
    if (imgUrl == null) {
        return
    }
    Glide.with(view.context).load(imgUrl)
        .into(view)
}

@BindingAdapter("bind_remain")
fun bindRemain(view: TextView, remain: Int?) {
    if (remain == null) {
        return
    }
    view.text = intToStr(remain, prefix = appStr(R.string.won_char) + " ")
}

@BindingAdapter("bind_current", "bind_max")
fun bindProgress(view: ProgressBar, current: Int?, max: Int? = 100) {
    if (current == null || max == null) {
        return
    }
    view.progress = (current.toDouble() / max.absoluteValue * 100).toInt()
}

@BindingAdapter("bind_date", "bind_dateFormat")
fun bindDate(view: TextView, date: Date?, dateFormat: String?) {
    if (date == null || dateFormat == null) {
        return
    }
    view.text = date.toString(dateFormat)
}

@BindingAdapter("bind_money")
fun bindMoney(view: TextView, money: Int?) {
    if (money == null) {
        return
    }
    view.text = intToStr(-money, suffix = appStr(R.string.won))
}

@BindingAdapter("bind_category")
fun bindCategory(view: Button, category: Int?) {
    if (category == null) {
        return
    }
    val categoryArr = appStrArray(R.array.category)
    val shape = view.background as GradientDrawable
    shape.color = appColorSL(R.color.colorPrimary)
    view.text = categoryArr[category]
}

@BindingAdapter("bind_expense")
fun bindExpense(view: TextView, expense: Int?) {
    if (expense == null) {
        return
    }
    view.text = intToStr(
        expense, suffix = " " + appStr(R.string.account_expense), prefix = appStr(R.string.won_char)
    )
}

@BindingAdapter("bind_average")
fun bindAverage(view: TextView, average: Int?) {
    if (average == null) {
        return
    }
    view.text = intToStr(
        average, prefix = appStr(R.string.account_average) + " " + appStr(R.string.won_char)
    )
}

@BindingAdapter("bind_date")
fun bindDate(view: TextView, date: Date?) {
    if (date == null) {
//        view.text = ""
        return
    }
    val cal = Calendar.getInstance().apply {
        time = date
    }
    view.text = date.toString("d")
    when (cal.get(Calendar.DAY_OF_WEEK)) {
        1 -> view.setTextColor(appColor(R.color.colorSunday))
        7 -> view.setTextColor(appColor(R.color.colorSaturday))
        else -> view.setTextColor(appColor(R.color.colorWeekday))
    }
}

@BindingAdapter("bind_category", "bind_startDate", "bind_endDate")
fun bindTitleTop(view: TextView, category: Int?, startDate: Date?, endDate: Date?) {
    if (category == null || startDate == null || endDate == null) {
        return
    }
    val categoryArr = appStrArray(R.array.category)
    val start = startDate.toString("MM.dd")
    val end = endDate.toString("MM.dd")
    val text = "[${categoryArr[category]}] $start ~ $end"
    view.text = text
}
