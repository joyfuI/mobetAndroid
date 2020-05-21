package maw.mobet.friend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_friend.view.*
import maw.mobet.api.MemberItem
import maw.mobet.databinding.ListItemFriendBinding

class MyAdapter(
    private val data: MutableList<MemberItem>, private val listener: OnItemClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, delete: () -> Unit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListItemFriendBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

        val onClickListener = View.OnClickListener {
            listener?.onItemClick(it, position) {
                holder.delete()
            }
        }
        holder.itemView.delete_btn.setOnClickListener(onClickListener)

        with (holder.itemView) {
            tag = item
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        val binding: ListItemFriendBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MemberItem) {
            binding.apply {
                item = data
                executePendingBindings()
            }
        }

        fun delete() {
            data.removeAt(adapterPosition)
            notifyItemRemoved(adapterPosition)
        }
    }
}
