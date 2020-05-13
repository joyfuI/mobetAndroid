package maw.mobet.invite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_invite.*
import maw.mobet.R

class MyAdapter(
    private val data: List<MemberItem2>,
    private val listener: OnCheckedChangeListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnCheckedChangeListener {
        fun onCheckedChange(view: View, checked: Boolean, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_invite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        Glide.with(holder.itemView).load(item.imgUrl)
            .into(holder.profile_img)
        holder.profile_txt.text = item.nick
        holder.check_box.setOnCheckedChangeListener(null) // 이거 없으면 사라질때 리스너 실행됨
        holder.check_box.isChecked = item.isChecked
        holder.check_box.setOnCheckedChangeListener { _, b ->
            item.isChecked = b
            listener?.onCheckedChange(holder.itemView, b, position)
        }

        with (holder.itemView) {
            tag = item
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer
}
