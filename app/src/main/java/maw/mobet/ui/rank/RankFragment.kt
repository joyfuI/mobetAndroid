package maw.mobet.ui.rank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_rank.*
import maw.mobet.R

class RankFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var viewModel: RankViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[RankViewModel::class.java]
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        list_view.layoutManager = LinearLayoutManager(activity)
        list_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        viewModel.list.observe(viewLifecycleOwner, Observer {
            list_view.adapter = MyAdapter(it)
            swipe_l.isRefreshing = false
        })

        swipe_l.setOnRefreshListener(this)
    }

    // 당겨서 새로고침
    override fun onRefresh() {
        viewModel.loadData()
    }
}
