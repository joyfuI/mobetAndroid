package maw.mobet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import maw.mobet.api.GameItem
import maw.mobet.databinding.ActivityGame2Binding
import maw.mobet.ui.game.GameViewModel
import maw.mobet.ui.game.MyPagerAdapter
import maw.mobet.ui.game.info.InfoFragment
import maw.mobet.ui.game.statistics.StatisticsFragment
import splitties.resources.txt
import splitties.toast.toast

class Game2Activity : AppCompatActivity() {
    private val statisticsFragment = StatisticsFragment.newInstance()
    private val infoFragment = InfoFragment.newInstance()
    private lateinit var viewModel: GameViewModel
    private lateinit var info: GameItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_game2)
        val binding: ActivityGame2Binding = DataBindingUtil.setContentView(
            this, R.layout.activity_game2
        )

        val id = intent.getIntExtra("id", -1)
        if (id == -1) {
            toast(R.string.access_error)
            finish()
            return
        }

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        viewModel.info.observe(this, Observer {
            info = it
            binding.game = info
        })
        val data = intent.getParcelableExtra<GameItem>("data")
        if (data == null) {
            viewModel.loadData(id)
        } else {
            info = data
            viewModel.loadData(info)
        }

        val tabText = listOf(
            txt(R.string.tab_game_statistics),
            txt(R.string.tab_game_info)
        )

        binding.viewPager.adapter = MyPagerAdapter(
            this, listOf(
                statisticsFragment,
                infoFragment
            )
        )
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabText[position]
        }.attach()
    }
}
