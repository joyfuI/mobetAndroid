package maw.mobet.ui.account.history

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_history_data.view.*
import kotlinx.android.synthetic.main.list_item_history_header.view.*
import maw.mobet.R
import maw.mobet.intToStr
import maw.mobet.toString

class MyAdapter(
    private val data: List<HistoryListItem>, private val listener: OnClickListener? = null
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onClick(view: View)
    }

    private val onClickListener = View.OnClickListener {
        listener?.onClick(it)
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == HistoryListItem.TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_history_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_history_data, parent, false)
            DataViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = holder.itemView.resources
        val item = data[position]

        if (item.getType() == HistoryListItem.TYPE_HEADER) {
            val headerHolder = holder as HeaderViewHolder
            val headerItem = item as HistoryListHeaderItem

            headerHolder.dateTxt.text = headerItem.date.toString(res.getString(R.string.month_day))
            if (headerItem.plus != 0) {
                headerHolder.plusTxt.text = intToStr(headerItem.plus, prefix = "+", suffix = res.getString(R.string.won))
                headerHolder.plusTxt.visibility = View.VISIBLE
            }
            if (headerItem.minus != 0) {
                headerHolder.minusTxt.text = intToStr(headerItem.minus, suffix = res.getString(R.string.won))
                headerHolder.minusTxt.visibility = View.VISIBLE
            }
        } else {
            val dataHolder = holder as DataViewHolder
            val dataItem = item as HistoryListDataItem

            val shape = dataHolder.dataBtn.background as GradientDrawable
            if (dataItem.money > 0) {
                shape.color = res.getColorStateList(
                    R.color.colorDeposit,
                    dataHolder.itemView.context.theme
                )
                dataHolder.dataBtn.text = res.getString(R.string.deposit)
                dataHolder.accountTxt.setTextColor(res.getColor(
                    R.color.colorDeposit,
                    dataHolder.itemView.context.theme
                ))
            } else {
                shape.color = res.getColorStateList(
                    R.color.colorWithdrawal,
                    dataHolder.itemView.context.theme
                )
                dataHolder.dataBtn.text = res.getString(R.string.withdrawal)
                dataHolder.accountTxt.setTextColor(res.getColor(
                    R.color.colorWithdrawal,
                    dataHolder.itemView.context.theme
                ))
            }
            dataHolder.nameTxt.text = dataItem.name
            dataHolder.accountTxt.text = intToStr(dataItem.money, suffix = res.getString(R.string.won))
        }

        with (holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = data.size

    abstract inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class DataViewHolder(itemView: View) : ViewHolder(itemView) {
        val dataBtn: Button = itemView.data_btn
        val nameTxt: TextView = itemView.name_txt
        val accountTxt: TextView = itemView.account_txt
    }

    inner class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {
        val dateTxt: TextView = itemView.date_txt
        val plusTxt: TextView = itemView.plus_txt
        val minusTxt: TextView = itemView.minus_txt
    }
}
