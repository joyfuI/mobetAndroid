package maw.mobet.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_account.*
import maw.mobet.R
import maw.mobet.ui.account.history.HistoryFragment
import maw.mobet.ui.account.statistics.StatisticsFragment
import splitties.resources.txt
import java.util.*

class AccountFragment : Fragment(), ViewModelStoreOwner {
    private val historyFragment = HistoryFragment.newInstance()
    private val statisticsFragment = StatisticsFragment.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tabText = listOf(
            txt(R.string.tab_account_history),
            txt(R.string.tab_account_statistics)
        )

        view_pager.adapter = MyPagerAdapter(this, listOf(
            historyFragment,
            statisticsFragment
        ))
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = tabText[position]
        }.attach()
    }

    fun selectDate(date: Date) {
        view_pager.currentItem = 0
        historyFragment.scrollToDate(date)
    }
}
