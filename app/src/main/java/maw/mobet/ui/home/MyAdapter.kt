package maw.mobet.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_home.*
import maw.mobet.R
import maw.mobet.api.HomeListItem
import maw.mobet.intToStr
import maw.mobet.toString
import splitties.dimensions.dip
import splitties.init.appCtx
import splitties.resources.appStr
import splitties.resources.appTxtArray
import kotlin.math.absoluteValue

class MyAdapter(
    private val data: List<HomeListItem>, private val listener: OnClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        Glide.with(holder.itemView).load(item.imgUrl)
            .apply(RequestOptions().circleCrop())
            .override(appCtx.dip(30))
            .into(holder.profile_img)
        holder.profile_txt.text = item.name
        holder.title_img.setImageResource(R.drawable.ic_launcher_background)
        val category = appTxtArray(R.array.category)
        val startDate = item.startDate.toString("MM.dd")
        val endDate = item.endDate.toString("MM.dd")
        val text = "[${category[item.category]}] $startDate ~ $endDate"
        holder.title_top_txt.text = text
        holder.title_txt.text = item.title
        holder.title_bottom_txt.text = intToStr(
            item.price.absoluteValue, prefix = appStr(R.string.won_char)
        )
        holder.title_bottom_img.setImageResource(if (item.price > 0) {
            R.drawable.ic_arrow_upward_24dp
        } else {
            R.drawable.ic_arrow_downward_24dp
        })

        val onClickListener = View.OnClickListener {
            listener?.onClick(it, position)
        }
        with (holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer
}
