package maw.mobet.ui.account.statistics

import android.content.res.ColorStateList
import android.graphics.Color
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
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_account_statistics.*
import kotlinx.android.synthetic.main.fragment_account_statistics.view.*
import maw.mobet.MainActivity
import maw.mobet.R
import maw.mobet.api.AccountItem
import maw.mobet.api.HistoryItem
import maw.mobet.ui.account.AccountViewModel
import maw.mobet.ui.account.history.HistoryListDataItem
import maw.mobet.ui.account.history.HistoryListHeaderItem
import maw.mobet.ui.account.history.HistoryListItem
import java.text.SimpleDateFormat
import java.util.*


class StatisticsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        fun newInstance() = StatisticsFragment()
    }
    var d : Int= 7

    private lateinit var viewModel: AccountViewModel
    lateinit var scheduleRecyclerViewAdapter: RecyclerViewAdapter
    private lateinit var listDate: AccountItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity ?: this)[AccountViewModel::class.java]
        return inflater.inflate(R.layout.fragment_account_statistics, container, false)
    }
    fun initView() {
        Log.d("dodo", "fragment_init")

        progressbar1.setProgress(80)
        progressbar1.tag = 1
        progressbar2.setProgress(70)
        progressbar2.tag = 2
        progressbar3.setProgress(60)
        progressbar3.tag = 3
        progressbar4.setProgress(50)
        progressbar4.tag = 4
        progressbar5.setProgress(40)
        progressbar5.tag = 5
        progressbar6.setProgress(30)
        progressbar6.tag = 6
        progressbar7.setProgress(20)
        progressbar7.tag = 7
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
        val text_test = sdf.format(calendar.time) +" 월"
        tv_current_month.text = text_test
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.list.observe(viewLifecycleOwner, Observer {
            listDate = it
            scheduleRecyclerViewAdapter = RecyclerViewAdapter(listDate, this)
            initView()
        })
        chart1.progressbar1.setOnClickListener{
            chart1.progressbar1.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(92,20,136)))
            chart2.progressbar2.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart3.progressbar3.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart4.progressbar4.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart5.progressbar5.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart6.progressbar6.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart7.progressbar7.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            val differ = d - (progressbar1.tag as Int)
            Log.d("dodo", differ.toString())
            d = (progressbar1.tag as Int)
            if( differ < 0){
                for(i in 0..(Math.abs(differ) -1))
                    scheduleRecyclerViewAdapter.changeToNextMonth()
            }
            else{
                for(i in 0..(differ-1))
                    scheduleRecyclerViewAdapter.changeToPrevMonth()
            }
            val a = tv_current_month.text.toString() + " 월"
            month_chart1.text = a
            Log.d("dodo", "chart1")
        }
        chart2.progressbar2.setOnClickListener{
            chart1.progressbar1.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart2.progressbar2.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(92,20,136)))
            chart3.progressbar3.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart4.progressbar4.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart5.progressbar5.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart6.progressbar6.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart7.progressbar7.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            val differ = d - (progressbar2.tag as Int)
            Log.d("dodo", differ.toString())
            d = (progressbar2.tag as Int)
            if( differ < 0){
                for(i in 0..(Math.abs(differ) -1))
                    scheduleRecyclerViewAdapter.changeToNextMonth()
            }
            else{
                for(i in 0..(differ-1))
                    scheduleRecyclerViewAdapter.changeToPrevMonth()
            }
            val a = tv_current_month.text.toString() + " 월"
            month_chart2.text = a
            Log.d("dodo", "chart2")
        }
        chart3.progressbar3.setOnClickListener{
            chart1.progressbar1.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart2.progressbar2.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart3.progressbar3.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(92,20,136)))
            chart4.progressbar4.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart5.progressbar5.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart6.progressbar6.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart7.progressbar7.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            val differ = d - (progressbar3.tag as Int)
            Log.d("dodo", differ.toString())
            d = (progressbar3.tag as Int)
            if( differ < 0){
                for(i in 0..(Math.abs(differ) -1))
                    scheduleRecyclerViewAdapter.changeToNextMonth()
            }
            else{
                for(i in 0..(differ-1))
                    scheduleRecyclerViewAdapter.changeToPrevMonth()
            }
            val a = tv_current_month.text.toString() + " 월"
            month_chart3.text = a
            Log.d("dodo", "chart3")
        }
        chart4.progressbar4.setOnClickListener{
            chart1.progressbar1.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart2.progressbar2.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart3.progressbar3.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart4.progressbar4.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(92,20,136)))
            chart5.progressbar5.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart6.progressbar6.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart7.progressbar7.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            val differ = d - (progressbar4.tag as Int)
            Log.d("dodo", differ.toString())

            d = (progressbar4.tag as Int)
            if( differ < 0){
                for(i in 0..(Math.abs(differ) -1))
                    scheduleRecyclerViewAdapter.changeToNextMonth()
            }
            else{
                for(i in 0..(differ-1))
                    scheduleRecyclerViewAdapter.changeToPrevMonth()
            }
            val a = tv_current_month.text.toString() + " 월"
            month_chart4.text = a
            Log.d("dodo", "chart4")
        }
        chart5.progressbar5.setOnClickListener{
            chart1.progressbar1.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart2.progressbar2.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart3.progressbar3.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart5.progressbar5.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(92,20,136)))
            chart4.progressbar4.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart6.progressbar6.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart7.progressbar7.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            val differ = d - (progressbar5.tag as Int)
            Log.d("dodo", differ.toString())
            d = (progressbar5.tag as Int)
            if( differ < 0){
                for(i in 0..(Math.abs(differ) -1))
                    scheduleRecyclerViewAdapter.changeToNextMonth()
            }
            else{
                for(i in 0..(differ-1))
                    scheduleRecyclerViewAdapter.changeToPrevMonth()
            }
            val a = tv_current_month.text.toString() + " 월"
            month_chart5.text = a
            Log.d("dodo", "chart5")
        }
        chart6.progressbar6.setOnClickListener{
            chart1.progressbar1.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart2.progressbar2.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart3.progressbar3.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart6.progressbar6.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(92,20,136)))
            chart5.progressbar5.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart4.progressbar4.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart7.progressbar7.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            val differ = d - (progressbar6.tag as Int)
            Log.d("dodo", differ.toString())
            d = (progressbar6.tag as Int)
            if( differ < 0){
                for(i in 0..(Math.abs(differ) -1))
                    scheduleRecyclerViewAdapter.changeToNextMonth()
            }
            else{
                for(i in 0..(differ-1))
                    scheduleRecyclerViewAdapter.changeToPrevMonth()
            }
            val a = tv_current_month.text.toString() + " 월"
            month_chart6.text = a
            Log.d("dodo", "chart6")
        }
        chart7.progressbar7.setOnClickListener{
            chart1.progressbar1.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart2.progressbar2.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart3.progressbar3.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart7.progressbar7.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(92,20,136)))
            chart5.progressbar5.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart6.progressbar6.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            chart4.progressbar4.progressTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(
                Color.rgb(224,224,224)))
            val differ = d - (progressbar7.tag as Int)
            Log.d("dodo", differ.toString())
            d = (progressbar7.tag as Int)
            if( differ < 0){
                for(i in 0..(Math.abs(differ) -1))
                    scheduleRecyclerViewAdapter.changeToNextMonth()
            }
            else{
                for(i in 0..(differ-1))
                    scheduleRecyclerViewAdapter.changeToPrevMonth()
            }
            val a = tv_current_month.text.toString() + " 월"
            month_chart7.text = a
            Log.d("dodo", "chart7")
        }
        // TODO: Use the ViewModel
    }
    override fun onRefresh() {
        viewModel.loadData()
    }
}
