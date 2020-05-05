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
import maw.mobet.api.HistoryItem
import maw.mobet.ui.account.AccountViewModel
import java.util.*
import kotlin.collections.ArrayList

class HistoryFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity ?: this)[AccountViewModel::class.java]
        return inflater.inflate(R.layout.fragment_account_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        list_view.layoutManager = LinearLayoutManager(activity)
        viewModel.list.observe(viewLifecycleOwner, Observer {
            list_view.adapter = MyAdapter(createList(it))
            swipe_l.isRefreshing = false
        })

        swipe_l.setOnRefreshListener(this)
    }

    // 당겨서 새로고침
    override fun onRefresh() {
        viewModel.loadData()
    }

    fun createList(data: List<HistoryItem>): List<HistoryListItem> {
        val list = ArrayList<HistoryListItem>()
        val dateTmp = Calendar.getInstance()
        dateTmp.set(0, 0, 0)
        var header: HistoryListHeaderItem? = null

        for (i in data) {
            val date = Calendar.getInstance()
            date.time = i.date

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
            list.add(HistoryListDataItem(i.name, i.money))
        }
        return list
    }
}
