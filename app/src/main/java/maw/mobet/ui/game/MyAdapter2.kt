package maw.mobet.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import maw.mobet.api.GameItem
import maw.mobet.api.MemberItem
import maw.mobet.databinding.ListItemMemberStatisticsBinding

class MyAdapter2(
    private val data: List<MemberItem>,
    private val game: GameItem,
    private val listener: OnClickListener? = null
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
        holder.bind(item, game)

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
        fun bind(member: MemberItem, game: GameItem) {
            binding.apply {
                this.member = member
                this.game = game
                executePendingBindings()
            }
        }
    }
}
