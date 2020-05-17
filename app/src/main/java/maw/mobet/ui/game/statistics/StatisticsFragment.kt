package maw.mobet.ui.game.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import maw.mobet.*
import maw.mobet.api.GameItem
import maw.mobet.api.MemberItem
import maw.mobet.databinding.FragmentGameStatisticsBinding
import maw.mobet.ui.game.GameViewModel
import maw.mobet.ui.game.MyAdapter2
import splitties.resources.str
import splitties.toast.toast
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

            var my: MemberItem? = null
            val your = mutableListOf<MemberItem>()
            for (i in info.members) {
                if (i.id == User.id) {
                    my = i
                } else {
                    your.add(i)
                }
            }

            if (my == null) {
                toast(R.string.access_error)
                requireActivity().finish()
            }

            val cal = Calendar.getInstance().apply {
                time = info.endDate
            }
            var html = str(R.string.game_remain)
            html = html.replace("{0}", (cal.get(Calendar.MONTH) + 1).toString())
            html = html.replace("{1}", cal.get(Calendar.DAY_OF_MONTH).toString())
            html = html.replace("{2}", diffDate(today, info.endDate).toString())
            binding.remainTxt.text = html.fromHtml()

            binding.listView.adapter = MyAdapter2(your, info)
            binding.my = my
            binding.game = info
        })
        viewModel.loadData(info)

        binding.listView.layoutManager = LinearLayoutManager(context)
    }
}
