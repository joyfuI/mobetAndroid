package maw.mobet.ui.my.playing

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
import splitties.resources.appTxtArray

class MyplayingAdapter(
    private val data: List<GameItem>, private val listener: OnItemClickListener? = null
) : RecyclerView.Adapter<MyplayingAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_my, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: MyplayingAdapter.ViewHolder, position: Int) {
        val item = data[position]
        val my = item.members.find {
            it.id == User.id
        }!!

        Log.d("dodo","adapter_playing + ${item.admin.nick}")

        Glide.with(holder.itemView).load(item.admin.imgUrl)
            .into(holder.profile_my_img)
        holder.profile_nick.text = item.admin.nick
        holder.title_txt.text = item.title
        val category = appTxtArray(R.array.category)
        val text = "[${category[item.category]}]" +
                " D - ${item.endDate.time  - item.startDate.time}"
        holder.category_txt.text = text
        val text2 = "남은 금액 : " + "${my.remain}"
        holder.amount.text = text2
        holder.rank_txt.text = my.place.toString()

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