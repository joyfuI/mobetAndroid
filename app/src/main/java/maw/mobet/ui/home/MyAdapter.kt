package maw.mobet.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import maw.mobet.api.GameItem
import maw.mobet.databinding.ListItemHomeBinding

class MyAdapter(
    private val data: List<GameItem>, private val listener: OnItemClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListItemHomeBinding
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
        val binding: ListItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GameItem) {
            binding.apply {
                item = data
                executePendingBindings()
            }
        }
    }
}
