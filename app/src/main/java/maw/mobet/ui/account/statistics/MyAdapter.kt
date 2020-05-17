package maw.mobet.ui.account.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import maw.mobet.databinding.ListItemCalendarBinding

class MyAdapter(
    private val data: List<CalendarItem>, private val listener: OnItemClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListItemCalendarBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
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

    inner class ViewHolder(
        val binding: ListItemCalendarBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CalendarItem) {
            binding.apply {
                this.item = data
                executePendingBindings()
            }
        }
    }
}
