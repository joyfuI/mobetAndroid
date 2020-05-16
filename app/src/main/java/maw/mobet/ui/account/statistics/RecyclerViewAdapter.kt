package maw.mobet.ui.account.statistics


import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_calendar.*
import maw.mobet.R
import java.util.*



class RecyclerViewAdapter(val StaticActivity: StatisticsFragment) : RecyclerView.Adapter<ViewHolderHelper>() {

    val baseCalendar = BaseCalendar()

    init {
        Log.d("dodo", "start")
        baseCalendar.initBaseCalendar {
            refreshView(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHelper {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_calendar, parent, false)
        return ViewHolderHelper(view)
    }

    override fun getItemCount(): Int {
        return BaseCalendar.LOW_OF_CALENDAR * BaseCalendar.DAYS_OF_WEEK
    }

    override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
        Log.d("dodo", "viewholder")

        if (position % BaseCalendar.DAYS_OF_WEEK == 0)  holder.tv_date.setTextColor(Color.parseColor("#f29494"))
        else if (position % BaseCalendar.DAYS_OF_WEEK == 6) holder.tv_date.setTextColor(Color.parseColor("#a5a2e8"))
        else holder.tv_date.setTextColor(Color.parseColor("#bf151026"))


        if (position < baseCalendar.prevMonthTailOffset || position >= baseCalendar.prevMonthTailOffset + baseCalendar.currentMonthMaxDate) {
            holder.tv_date.alpha = 0.3f
        } else {
            holder.tv_date.alpha = 1f
        }
        holder.tv_date.text = baseCalendar.data[position].toString()
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
//        StaticActivity.refreshCurrentMonth(calendar)
    }
}