package maw.mobet.ui.game

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import maw.mobet.R
import maw.mobet.api.MemberItem
import maw.mobet.intToStr
import splitties.resources.appStr
import splitties.resources.appStrArray
import kotlin.math.absoluteValue

@BindingAdapter("bind_greater", "bind_less")
fun bindCondition(view: TextView, greater: Int?, less: Int?) {
    if (greater == null || less == null) {
        return
    }
    val str = intToStr(greater, prefix = appStr(R.string.won_char)) +
            " ~ " +
            intToStr(less, prefix = appStr(R.string.won_char))
    view.text = str
}

@BindingAdapter("bind_price")
fun bindPrice(view: TextView, price: Int) {
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

@BindingAdapter("bind_category")
fun bindCategory(view: TextView, category: Int) {
    val categoryArr = appStrArray(R.array.category)
    view.text = categoryArr[category]
}

@BindingAdapter("bind_adapter")
fun bindAdapter(view: RecyclerView, members: List<MemberItem>?) {
    view.adapter = MyAdapter(members ?: return)
}
