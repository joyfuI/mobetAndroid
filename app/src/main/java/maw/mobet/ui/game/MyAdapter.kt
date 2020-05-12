package maw.mobet.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_member.*
import maw.mobet.R
import maw.mobet.api.MemberItem

class MyAdapter(
    private val data: List<MemberItem>, private val listener: OnClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_member, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        Glide.with(holder.itemView).load(item.imgUrl)
            .into(holder.profile_img)
        holder.profile_txt.text = item.nick

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
