package maw.mobet.ui.my.finish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_rank.*
import maw.mobet.R
import maw.mobet.api.MemberItem
import maw.mobet.intToStr
import maw.mobet.ui.my.finish.MyfinishAdapter.ViewHolder
import maw.mobet.ui.rank.MyAdapter

class DialogAdapter(
    private val data: List<MemberItem>) : RecyclerView.Adapter<DialogAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_rank, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: DialogAdapter.ViewHolder, position: Int) {
        val item = data[position]

        holder.rank_txt.text = item.rank.toString()
        Glide.with(holder.itemView).load(item.imgUrl)
            .into(holder.profile_img)
        holder.profile_txt.text = item.nick
        holder.grade_btn.text = item.grade
        holder.score_txt.text = intToStr(item.score)
    }
    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer
}