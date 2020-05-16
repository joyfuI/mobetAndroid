package maw.mobet.ui.account.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import maw.mobet.databinding.ListItemCalendarBinding

class MyAdapter(
    private val data: AccountItem2, private val listener: OnClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListItemCalendarBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data
        holder.bind(0, 0)

        val onClickListener = View.OnClickListener {
            listener?.onClick(it, position)
        }
        with (holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = 0

    inner class ViewHolder(
        val binding: ListItemCalendarBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Int, sum: Int) {
            binding.apply {
                this.day = day
                this.sum = sum
                executePendingBindings()
            }
        }
    }
}
