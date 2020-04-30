package maw.mobet.ui.account.statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_account_statistics.*
import maw.mobet.MainActivity
import maw.mobet.R
import maw.mobet.ui.account.AccountViewModel
import java.text.SimpleDateFormat
import java.util.*


class StatisticsFragment : Fragment() {
    companion object {
        fun newInstance() = StatisticsFragment()
    }

    private lateinit var viewModel: AccountViewModel
    lateinit var scheduleRecyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //initView()
        viewModel = ViewModelProvider(activity ?: this)[AccountViewModel::class.java]
        return inflater.inflate(R.layout.fragment_account_statistics, container, false)
    }
    fun initView() {
        Log.d("dodo", "fragment_init")

        scheduleRecyclerViewAdapter = RecyclerViewAdapter(this)

        rv_schedule.layoutManager = GridLayoutManager(
                activity,
                BaseCalendar.DAYS_OF_WEEK
        )
        rv_schedule.adapter = scheduleRecyclerViewAdapter
//        rv_schedule.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
//        rv_schedule.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }
    fun refreshCurrentMonth(calendar: Calendar) {
        Log.d("dodo", "fragment_currentmonth")
        val sdf = SimpleDateFormat("MM", Locale.KOREAN)
        tv_current_month.text = sdf.format(calendar.time)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}
