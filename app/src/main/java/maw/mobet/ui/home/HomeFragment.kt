package maw.mobet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_home.*
import maw.mobet.GameActivity
import maw.mobet.R
import maw.mobet.api.GameItem
import splitties.fragments.start

class HomeFragment : Fragment(), MyAdapter.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        list_view.layoutManager = LinearLayoutManager(activity)
        list_view.addItemDecoration(MyItemDecoration())
        viewModel.list.observe(viewLifecycleOwner, Observer {
            list_view.adapter = MyAdapter(it, this)
            swipe_l.isRefreshing = false
        })

        swipe_l.setOnRefreshListener(this)
    }

    // 리스트 아이템 클릭
    override fun onClick(view: View, position: Int) {
        val item = view.tag as GameItem

        start<GameActivity> {
            putExtra("id", item.id)
            putExtra("data", item)
        }
    }

    // 당겨서 새로고침
    override fun onRefresh() {
        viewModel.loadData()
    }

    fun refresh() {
        swipe_l.isRefreshing = true
        viewModel.loadData()
    }
}
