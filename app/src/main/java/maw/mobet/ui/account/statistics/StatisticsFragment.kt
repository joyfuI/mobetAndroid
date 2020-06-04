package maw.mobet.ui.account.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_account_statistics.*
import maw.mobet.R
import maw.mobet.api.AccountItem
import maw.mobet.api.MonthItem
import maw.mobet.databinding.FragmentAccountStatisticsBinding
import maw.mobet.ui.account.AccountFragment
import maw.mobet.ui.account.AccountViewModel
import java.util.*

class StatisticsFragment : Fragment(), View.OnClickListener, MyAdapter.OnItemClickListener {
    companion object {
        fun newInstance() = StatisticsFragment()
    }

    private lateinit var binding: FragmentAccountStatisticsBinding
    private lateinit var viewModel: AccountViewModel
    private lateinit var data: AccountItem2
    private lateinit var historyData: MutableMap<Date, Int>
    private var position = 5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(parentFragment as AccountFragment)[AccountViewModel::class.java]
//        return inflater.inflate(R.layout.fragment_account_statistics, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_statistics, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.list.observe(viewLifecycleOwner, Observer {
            data = createItem(it)

            historyData = mutableMapOf()
            val dateTmp = Calendar.getInstance().apply {
                set(0, 0, 0)
            }
            for (i in data.account.history) {
                val date = Calendar.getInstance().apply {
                    time = i.date
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                if (
                    date.get(Calendar.YEAR) != dateTmp.get(Calendar.YEAR) ||
                    date.get(Calendar.MONTH) != dateTmp.get(Calendar.MONTH) ||
                    date.get(Calendar.DATE) != dateTmp.get(Calendar.DATE)
                ) {
                    dateTmp.time = i.date
                    historyData[date.time] = i.money
                } else {
                    historyData[date.time] = historyData[date.time]!! + i.money
                }
            }

            binding.listView.adapter = MyAdapter(
                createList(data.account.month[data.position].month),
                this
            )
            binding.data = data
        })

        binding.scrollView.post {
            binding.scrollView.scrollX = binding.scrollView.getChildAt(0).width
        }
        binding.listView.layoutManager = GridLayoutManager(activity, 7)
//        binding.listView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.HORIZONTAL))
//        binding.listView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        graph0.setOnClickListener(this)
        graph1.setOnClickListener(this)
        graph2.setOnClickListener(this)
        graph3.setOnClickListener(this)
        graph4.setOnClickListener(this)
        graph5.setOnClickListener(this)
    }

    private fun createItem(data: AccountItem): AccountItem2 {
        var max = 0
        var sum = 0

        val monthList = data.month.toMutableList()
        for (i in monthList) {
            if (max < i.sum) {
                max = i.sum
            }
            sum += i.sum
        }
        while (monthList.size != 6) {
            val cal = Calendar.getInstance().apply {
                time = monthList[0].month
                add(Calendar.MONTH, -1)
            }
            monthList.add(0, MonthItem(cal.time, 0))
        }

        return AccountItem2(position, AccountItem(data.history, monthList), max, sum / 6)
    }

    private fun createList(data: Date): List<CalendarItem> {
        val calendarList = mutableListOf<CalendarItem>()
        val cal = Calendar.getInstance().apply {
            time = data
            set(Calendar.DAY_OF_MONTH, 1)
        }

        var start = cal.get(Calendar.DAY_OF_WEEK)
        val end = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        var sum = 0
        for (i in 1 until start) {
            calendarList.add(CalendarItem())
        }
        for (i in 1 .. end) {
            val dateSum = historyData[cal.time]
            if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                sum = 0
            }
            sum += dateSum ?: 0
            calendarList.add(CalendarItem(
                cal.time,
                dateSum,
                if (cal.get(Calendar.DAY_OF_WEEK) == 7) sum else null)
            )
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
        start = cal.run {
            add(Calendar.MONTH, -1)
            set(Calendar.DAY_OF_MONTH, end)
            get(Calendar.DAY_OF_WEEK)
        }
        for (i in 1 .. 7 - start) {
            cal.add(Calendar.DAY_OF_MONTH, 1)
            calendarList.add(CalendarItem(
                null,
                null,
                if (cal.get(Calendar.DAY_OF_WEEK) == 7) sum else null
            ))
        }

        return calendarList
    }

    override fun onClick(p0: View?) {
        position = when (p0) {
            graph0 -> 0
            graph1 -> 1
            graph2 -> 2
            graph3 -> 3
            graph4 -> 4
            graph5 -> 5
            else -> 5
        }
        binding.data = data.apply {
            position = this@StatisticsFragment.position
        }
        binding.listView.adapter = MyAdapter(
            createList(data.account.month[data.position].month),
            this
        )
    }

    override fun onItemClick(view: View, date: Date?) {
        if (date == null) {
            return
        }
        (parentFragment as AccountFragment).selectDate(date)
    }
}
