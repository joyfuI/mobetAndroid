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
import maw.mobet.databinding.FragmentAccountStatisticsBinding
import maw.mobet.ui.account.AccountFragment
import maw.mobet.ui.account.AccountViewModel

class StatisticsFragment : Fragment(), View.OnClickListener {
    companion object {
        fun newInstance() = StatisticsFragment()
    }

    private lateinit var binding: FragmentAccountStatisticsBinding
    private lateinit var viewModel: AccountViewModel
    private lateinit var data: AccountItem2
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

//        list_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.HORIZONTAL))
//        list_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        viewModel.list.observe(viewLifecycleOwner, Observer {
            data = createList(it)

//            binding.listView.adapter = MyAdapter(data)
            binding.data = data
        })

        list_view.layoutManager = GridLayoutManager(activity, 7)

        graph0.setOnClickListener(this)
        graph1.setOnClickListener(this)
        graph2.setOnClickListener(this)
        graph3.setOnClickListener(this)
        graph4.setOnClickListener(this)
        graph5.setOnClickListener(this)
    }

    private fun createList(data: AccountItem): AccountItem2 {
        var max = 0
        var sum = 0

        for (i in data.month) {
            if (max < i.sum) {
                max = i.sum
            }
            sum += i.sum
        }

        return AccountItem2(position, data, max, sum / 6)
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
    }
}
