package maw.mobet.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import maw.mobet.databinding.ListItemMemberStatisticsBinding
import maw.mobet.ui.game.statistics.StatisticsFragment

class MyAdapter2(
    private val data: List<StatisticsFragment.Info>, private val listener: OnClickListener? = null
) : RecyclerView.Adapter<MyAdapter2.ViewHolder>() {
    interface OnClickListener {
        fun onClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListItemMemberStatisticsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

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
        val binding: ListItemMemberStatisticsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StatisticsFragment.Info) {
            binding.apply {
                info = data
                executePendingBindings()
            }
        }
    }
}
