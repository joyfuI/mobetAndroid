package maw.mobet.ui.account.statistics


import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_schedule.*
import kotlinx.android.synthetic.main.item_schedule.view.*
import maw.mobet.R
import maw.mobet.api.AccountItem
import maw.mobet.api.HistoryItem
import maw.mobet.ui.account.history.HistoryListItem
import maw.mobet.ui.account.history.MyAdapter
import java.util.*



class RecyclerViewAdapter(private val data: AccountItem, val StaticActivity: StatisticsFragment, private val listener: RecyclerViewAdapter.OnClickListener? = null) : RecyclerView.Adapter<ViewHolderHelper>() {
    interface OnClickListener {
        fun onClick(view: View, position: Int)
    }
    val baseCalendar = BaseCalendar()

    init {
        Log.d("dodo", "start")
        baseCalendar.initBaseCalendar {
            refreshView(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHelper {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ViewHolderHelper(view)
    }

    override fun getItemCount(): Int {
        return BaseCalendar.LOW_OF_CALENDAR * BaseCalendar.DAYS_OF_WEEK
    }

    override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
        Log.d("dodo", "viewholder")
        val item = data.month
        if (position % BaseCalendar.DAYS_OF_WEEK == 0){
            holder.tv_date.setTextColor(Color.parseColor("#f29494"))
        }
        else if (position % BaseCalendar.DAYS_OF_WEEK == 6){
            holder.tv_money.visibility = View.VISIBLE
            holder.tv_money.text = item[position/6 -1].sum.toString()
            holder.tv_date.setTextColor(Color.parseColor("#a5a2e8"))
        }
        else{
            holder.tv_date.setTextColor(Color.parseColor("#bf151026"))
            holder.tv_money.visibility = View.INVISIBLE
        }


        if (position < baseCalendar.prevMonthTailOffset || position >= baseCalendar.prevMonthTailOffset + baseCalendar.currentMonthMaxDate) {
            holder.tv_date.alpha = 0.3f
        } else {
            holder.tv_date.alpha = 1f
        }
        var startdate:String = ""
        for(i in 1..position){
            if (baseCalendar.data[position].toString() == "1")
                startdate = baseCalendar.data[position].toString()
        }
        holder.tv_date.text = baseCalendar.data[position].toString()
        //holder.tv_pay.text = data.history[position].money.toString()
        val onClickListener = View.OnClickListener {
            listener?.onClick(it, position)
        }
    }

    fun changeToPrevMonth() {
        baseCalendar.changeToPrevMonth {
            refreshView(it)
        }
    }

    fun changeToNextMonth() {
        baseCalendar.changeToNextMonth {
            refreshView(it)
        }
    }

    private fun refreshView(calendar: Calendar) {
        notifyDataSetChanged()
        StaticActivity.refreshCurrentMonth(calendar)
    }
}