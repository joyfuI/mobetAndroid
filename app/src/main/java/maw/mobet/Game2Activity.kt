package maw.mobet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_game2.*
import maw.mobet.ui.game.GameViewModel
import maw.mobet.ui.game.MyPagerAdapter
import maw.mobet.ui.game.info.InfoFragment
import maw.mobet.ui.game.statistics.StatisticsFragment
import splitties.resources.txt

class Game2Activity : AppCompatActivity() {
    private val statisticsFragment = StatisticsFragment.newInstance()
    private val infoFragment = InfoFragment.newInstance()
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game2)

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        val tabText = listOf(
            txt(R.string.tab_game_statistics),
            txt(R.string.tab_game_info)
        )

        view_pager.adapter = MyPagerAdapter(
            this, listOf(
                statisticsFragment,
                infoFragment
            )
        )
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = tabText[position]
        }.attach()
    }
}
