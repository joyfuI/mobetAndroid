package maw.mobet.notify

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_notify.*
import kotlinx.android.synthetic.main.list_item_notify.view.*
import maw.mobet.R
import maw.mobet.api.NotifyItem
import splitties.resources.appColorSL
import splitties.resources.appStr

class MyAdapter(
    private val data: MutableList<NotifyItem>, private val listener: OnClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onClick(view: View, position: Int, delete: () -> Unit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_notify, parent, false)
        var shape: GradientDrawable

        shape = view.accept_btn.background as GradientDrawable
        shape.color = appColorSL(R.color.colorDeposit)
        shape = view.reject_btn.background as GradientDrawable
        shape.color = appColorSL(R.color.colorWithdrawal)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        Glide.with(holder.itemView).load(item.member.imgUrl)
            .into(holder.profile_img)
        holder.profile_txt.text = item.member.nick
        var msg = appStr(R.string.notify_msg)
        msg = msg.replace("{0}", item.member.nick)
        msg = msg.replace("{1}", item.title)
        holder.msg_txt.text = msg

        val onClickListener = View.OnClickListener {
            listener?.onClick(it, position) {
                holder.delete()
            }
        }
        holder.accept_btn.setOnClickListener(onClickListener)
        holder.reject_btn.setOnClickListener(onClickListener)

        holder.accept_btn.tag = item
        holder.reject_btn.tag = item
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun delete() {
            data.removeAt(adapterPosition)
            notifyItemRemoved(adapterPosition)
        }
    }
}
