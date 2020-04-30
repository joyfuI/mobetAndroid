package maw.mobet.ui.account.history

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_history_data.*
import kotlinx.android.synthetic.main.list_item_history_header.*
import maw.mobet.R
import maw.mobet.intToStr
import maw.mobet.toString
import splitties.resources.appColor
import splitties.resources.appColorSL
import splitties.resources.appStr
import splitties.resources.appTxt

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
        val item = data[position]

        if (item.getType() == HistoryListItem.TYPE_HEADER) {
            val headerHolder = holder as HeaderViewHolder
            val headerItem = item as HistoryListHeaderItem

            headerHolder.date_txt.text = headerItem.date.toString(appStr(R.string.month_day))
            if (headerItem.plus != 0) {
                headerHolder.plus_txt.text = intToStr(headerItem.plus, prefix = "+", suffix = appStr(R.string.won))
                headerHolder.plus_txt.visibility = View.VISIBLE
            }
            if (headerItem.minus != 0) {
                headerHolder.minus_txt.text = intToStr(headerItem.minus, suffix = appStr(R.string.won))
                headerHolder.minus_txt.visibility = View.VISIBLE
            }
        } else {
            val dataHolder = holder as DataViewHolder
            val dataItem = item as HistoryListDataItem

            val shape = dataHolder.data_btn.background as GradientDrawable
            if (dataItem.money > 0) {
                shape.color = appColorSL(R.color.colorDeposit)
                dataHolder.data_btn.text = appTxt(R.string.deposit)
                dataHolder.account_txt.setTextColor(appColor(R.color.colorDeposit))
            } else {
                shape.color = appColorSL(R.color.colorWithdrawal)
                dataHolder.data_btn.text = appTxt(R.string.withdrawal)
                dataHolder.account_txt.setTextColor(appColor(R.color.colorWithdrawal))
            }
            dataHolder.name_txt.text = dataItem.name
            dataHolder.account_txt.text = intToStr(dataItem.money, suffix = appStr(R.string.won))
        }

        with (holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = data.size

    abstract inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer

    inner class DataViewHolder(containerView: View) : ViewHolder(containerView)
    inner class HeaderViewHolder(containerView: View) : ViewHolder(containerView)
}
