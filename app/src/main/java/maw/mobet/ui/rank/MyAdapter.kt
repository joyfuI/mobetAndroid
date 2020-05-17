package maw.mobet.ui.rank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_rank.*
import maw.mobet.R
import maw.mobet.api.MemberItem
import maw.mobet.intToStr

class MyAdapter(
    private val data: List<MemberItem>, private val listener: OnItemClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_rank, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.rank_txt.text = item.rank.toString()
        Glide.with(holder.itemView).load(item.imgUrl)
            .into(holder.profile_img)
        holder.profile_txt.text = item.nick
        holder.grade_btn.text = item.grade
        holder.score_txt.text = intToStr(item.score)

        val onClickListener = View.OnClickListener {
            listener?.onItemClick(it, position)
        }
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer
}
