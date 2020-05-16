package maw.mobet

import android.graphics.drawable.GradientDrawable
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import maw.mobet.api.MemberItem
import maw.mobet.ui.game.MyAdapter
import maw.mobet.ui.game.MyAdapter2
import maw.mobet.ui.game.statistics.StatisticsFragment
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

@BindingAdapter("bind_category")
fun bindCategory(view: ImageView, category: Int) {
    val backgroundArr = view.resources.obtainTypedArray(R.array.category_background)
    view.setImageResource(backgroundArr.getResourceId(category, 0))
    backgroundArr.recycle()
}

@BindingAdapter("bind_adapter")
fun bindAdapter(view: RecyclerView, members: List<MemberItem>?) {
    view.adapter = MyAdapter(members ?: return)
}

@BindingAdapter("bind_adapter2")
fun bindAdapter2(view: RecyclerView, members: List<StatisticsFragment.Info>?) {
    view.adapter = MyAdapter2(members ?: return)
}

@BindingAdapter("bind_imgUrl")
fun bindImgUrl(view: CircleImageView, url: String) {
    Glide.with(view.context).load(url)
        .into(view)
}

@BindingAdapter("bind_remain")
fun bindRemain(view: TextView, remain: Int) {
    view.text = intToStr(remain, prefix = appStr(R.string.won_char) + " ")
}

@BindingAdapter("bind_current", "bind_max")
fun bindProgress(view: ProgressBar, current: Int, max: Int = 100) {
    view.progress = (current.toDouble() / max.absoluteValue * 100).toInt()
}

@BindingAdapter("bind_date")
fun bindDate(view: TextView, date: Date) {
    view.text = date.toString(appStr(R.string.month_day))
}

@BindingAdapter("bind_money")
fun bindMoney(view: TextView, price: Int) {
    view.text = intToStr(-price, suffix = appStr(R.string.won))
}

@BindingAdapter("bind_category")
fun bindCategory(view: Button, category: Int) {
    val categoryArr = appStrArray(R.array.category)
    val shape = view.background as GradientDrawable
    shape.color = appColorSL(R.color.colorCyan)
    view.text = categoryArr[category]
}

@BindingAdapter("bind_categoryColor")
fun bindCategoryColor(view: TextView, category: Int) {
    view.setTextColor(appColor(R.color.colorCyan))
}
