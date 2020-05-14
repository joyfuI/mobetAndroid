package maw.mobet.ui.game.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import maw.mobet.Game2Activity
import maw.mobet.R
import maw.mobet.api.GameItem
import maw.mobet.databinding.FragmentGameStatisticsBinding
import maw.mobet.diffDate
import maw.mobet.fromHtml
import maw.mobet.ui.game.GameViewModel
import splitties.resources.str
import java.util.*

class StatisticsFragment : Fragment() {
    companion object {
        fun newInstance() = StatisticsFragment()
    }

    private lateinit var binding: FragmentGameStatisticsBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var info: GameItem
    private val today = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as Game2Activity)[GameViewModel::class.java]
        info = viewModel.info.value!!
//        return inflater.inflate(R.layout.fragment_game_statistics, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_statistics, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.info.observe(viewLifecycleOwner, Observer {
            info = it

            val cal = Calendar.getInstance().apply {
                time = info.endDate
            }
            var html = str(R.string.game_remain)
            html = html.replace("{0}", (cal.get(Calendar.MONTH) + 1).toString())
            html = html.replace("{1}", cal.get(Calendar.DAY_OF_MONTH).toString())
            html = html.replace("{2}", diffDate(today, info.endDate).toString())
            binding.remainTxt.text = html.fromHtml()

            binding.game = info
        })
        viewModel.loadData(info)
    }
}
