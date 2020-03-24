package maw.mobet.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import maw.mobet.R
import maw.mobet.api.HomeListItem
import maw.mobet.dpToPx
import maw.mobet.intToWon
import kotlin.math.absoluteValue

class MyAdapter(private val data: List<HomeListItem>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    interface OnClickListener {
        fun onClick(view: View, position: Int)
    }

    lateinit var parentView: ViewGroup
    var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        parentView = parent
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_home, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(parentView).load(data[position].imgUrl)
            .apply(RequestOptions().circleCrop()).override(dpToPx(parentView.context, 30f).toInt())
            .into(holder.profileImg)
        holder.profileTxt.text = data[position].name
        holder.titleImg.setImageResource(R.drawable.ic_launcher_background)
        val text = "[${when (data[position].category) {
            0 -> "패스트푸드"
            1 -> "편의점"
            else -> ""
        }}] ${data[position].startDate} ~ ${data[position].endDate}"
        holder.titleTopTxt.text = text
        holder.titleTxt.text = data[position].title
        holder.titleBottomTxt.text = intToWon(data[position].price.absoluteValue)
        holder.titleBottomImg.setImageResource(if (data[position].price > 0) {
            R.drawable.ic_arrow_upward_black_24dp
        } else {
            R.drawable.ic_arrow_downward_black_24dp
        })
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        val profileImg: ImageView = itemView.findViewById(R.id.profile_img)
        val profileTxt: TextView = itemView.findViewById(R.id.profile_txt)
        val titleImg: ImageView = itemView.findViewById(R.id.title_img)
        val titleTopTxt: TextView = itemView.findViewById(R.id.title_top_txt)
        val titleTxt: TextView = itemView.findViewById(R.id.title_txt)
        val titleBottomTxt: TextView = itemView.findViewById(R.id.title_bottom_txt)
        val titleBottomImg: ImageView = itemView.findViewById(R.id.title_bottom_img)

        override fun onClick(p0: View?) {	// 클릭 리스너
            onClickListener?.onClick(p0!!, adapterPosition)
        }
    }
}
