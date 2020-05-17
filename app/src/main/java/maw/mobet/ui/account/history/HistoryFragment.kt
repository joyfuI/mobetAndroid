package maw.mobet.ui.account.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_account_history.*
import maw.mobet.R
import maw.mobet.api.AccountItem
import maw.mobet.ui.account.AccountFragment
import maw.mobet.ui.account.AccountViewModel
import java.util.*
import kotlin.collections.ArrayList

class HistoryFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: AccountViewModel
    private lateinit var listDate: List<HistoryListItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(parentFragment as AccountFragment)[AccountViewModel::class.java]
        return inflater.inflate(R.layout.fragment_account_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        list_view.layoutManager = LinearLayoutManager(activity)
        list_view.addItemDecoration(MyItemDecoration())
        viewModel.list.observe(viewLifecycleOwner, Observer {
            listDate = createList(it)
            list_view.adapter = MyAdapter(listDate)
            swipe_l.isRefreshing = false
        })

        swipe_l.setOnRefreshListener(this)
    }

    // 당겨서 새로고침
    override fun onRefresh() {
        viewModel.loadData()
    }

    private fun createList(data: AccountItem): List<HistoryListItem> {
        val list = ArrayList<HistoryListItem>()
        val dateTmp = Calendar.getInstance().apply {
            set(0, 0, 0)
        }
        var header: HistoryListHeaderItem? = null

        for (i in data.history) {
            val date = Calendar.getInstance().apply {
                time = i.date
            }
            if (
                date.get(Calendar.YEAR) != dateTmp.get(Calendar.YEAR) ||
                date.get(Calendar.MONTH) != dateTmp.get(Calendar.MONTH) ||
                date.get(Calendar.DATE) != dateTmp.get(Calendar.DATE)
            ) {
                dateTmp.time = i.date
                header = HistoryListHeaderItem(i.date)
                list.add(header)
            }
            header?.add(i.money)
            list.add(HistoryListDataItem(i.category, i.name, i.money))
        }
        return list
    }

    fun scrollToDate(date: Date): Int? {
        val cal = Calendar.getInstance().apply {
            time = date
        }
        var position: Int? = null
        for ((index, value) in listDate.withIndex()) {
            if (value is HistoryListHeaderItem) {
                val calTmp = Calendar.getInstance().apply {
                    time = value.date
                }
                if (
                    cal.get(Calendar.YEAR) == calTmp.get(Calendar.YEAR) &&
                    cal.get(Calendar.MONTH) == calTmp.get(Calendar.MONTH) &&
                    cal.get(Calendar.DATE) == calTmp.get(Calendar.DATE)
                ) {
                    position = index
                }
            }
        }

        if (position != null) {
            // 이상하게 맨밑으로 스크롤한 뒤 원하는 위치로 스크롤해야 작동
            list_view.scrollToPosition(list_view.adapter!!.itemCount - 1)
            list_view.post {
                list_view.scrollToPosition(position)
            }
        }

        return position
    }
}
