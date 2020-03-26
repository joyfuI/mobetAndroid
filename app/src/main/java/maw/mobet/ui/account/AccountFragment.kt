package maw.mobet.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_account.*
import maw.mobet.R
import maw.mobet.ui.account.history.HistoryFragment
import maw.mobet.ui.account.statistics.StatisticsFragment

class AccountFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tabText = listOf(
            resources.getString(R.string.tab_account_history),
            resources.getString(R.string.tab_account_statistics)
        )

        view_pager.adapter = MyPagerAdapter(this, listOf(
            HistoryFragment.newInstance(),
            StatisticsFragment.newInstance()
        ))
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = tabText[position]
        }.attach()
    }
}
