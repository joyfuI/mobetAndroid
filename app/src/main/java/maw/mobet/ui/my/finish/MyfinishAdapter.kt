package maw.mobet.ui.my.finish

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_my.*
import maw.mobet.R
import maw.mobet.User
import maw.mobet.api.GameItem
import maw.mobet.diffDate
import maw.mobet.intToStr
import splitties.resources.appColor
import splitties.resources.appColorSL
import splitties.resources.appTxtArray
import java.util.*
import kotlin.math.absoluteValue

class MyfinishAdapter (
    private val data: List<GameItem>, private val listener: OnItemClickListener? = null
) : RecyclerView.Adapter<MyfinishAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_my, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: MyfinishAdapter.ViewHolder, position: Int) {
        val item = data[position]
        val my = item.members.find {
            it.id == User.id
        }!!

        Log.d("dodo","adapter_finish + ${item.admin.nick}")
        Glide.with(holder.itemView).load(item.admin.imgUrl)
            .into(holder.profile_my_img)
        holder.profile_nick.text = item.admin.nick
        holder.title_txt.text = item.title
        val category = appTxtArray(R.array.category)
        val text = "[${category[item.category]}] " + diffDate(item.startDate,item.endDate) + " 일"
        holder.category_txt.text = text
        val text2 = intToStr(my.remain!!,"사용 금액 : ","/") +
                data[position].price.absoluteValue + " 원"
        holder.category_txt.text = text
        holder.amount.text =text2

            val rank_personal = item.members.find {
            it.id == User.id
        }
        holder.rank_txt.text = rank_personal?.place.toString()
        val background = holder.rank_txt.background as GradientDrawable
        if(rank_personal?.place == 1){
            background.color = appColorSL(R.color.colorPrimary)
        }
        else{
            background.color = appColorSL(R.color.colorControlNormal)
        }
        Log.d("dodo", rank_personal?.place.toString())
        val onClickListener = View.OnClickListener {
            listener?.onItemClick(it, position)
        }
        with (holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }
    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer

}