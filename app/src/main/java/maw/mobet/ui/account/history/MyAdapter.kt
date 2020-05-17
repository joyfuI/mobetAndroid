package maw.mobet.ui.account.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import maw.mobet.databinding.ListItemHistoryDataBinding
import maw.mobet.databinding.ListItemHistoryHeaderBinding

class MyAdapter(
    private val data: List<HistoryListItem>, private val listener: OnItemClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun getItemViewType(position: Int): Int = data[position].getType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == HistoryListItem.TYPE_HEADER) {
            val view = ListItemHistoryHeaderBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            HeaderViewHolder(view)
        } else {
            val view = ListItemHistoryDataBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            DataViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

        val onClickListener = View.OnClickListener {
            listener?.onItemClick(it, position)
        }
        with (holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = data.size

    abstract inner class ViewHolder(
        open val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(data: HistoryListItem)
    }

    inner class DataViewHolder(
        override val binding: ListItemHistoryDataBinding
    ) : ViewHolder(binding) {
        override fun bind(data: HistoryListItem) {
            binding.apply {
                item = data as HistoryListDataItem
                executePendingBindings()
            }
        }
    }
    inner class HeaderViewHolder(
        override val binding: ListItemHistoryHeaderBinding
    ) : ViewHolder(binding) {
        override fun bind(data: HistoryListItem) {
            binding.apply {
                item = data as HistoryListHeaderItem
                executePendingBindings()
            }
        }
    }
}
