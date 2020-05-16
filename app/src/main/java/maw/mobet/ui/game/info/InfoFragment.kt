package maw.mobet.ui.game.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.game_info.*
import maw.mobet.Game2Activity
import maw.mobet.R
import maw.mobet.api.GameItem
import maw.mobet.databinding.FragmentGameInfoBinding
import maw.mobet.ui.game.GameViewModel
import maw.mobet.ui.game.MyAdapter

class InfoFragment : Fragment() {
    companion object {
        fun newInstance() = InfoFragment()
    }

    private lateinit var binding: FragmentGameInfoBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var info: GameItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as Game2Activity)[GameViewModel::class.java]
        info = viewModel.info.value!!
//        return inflater.inflate(R.layout.fragment_game_info, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_info, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.info.observe(viewLifecycleOwner, Observer {
            info = it

            list_view.adapter = MyAdapter(info.members)
            binding.game = info
        })
        viewModel.loadData(info)

        list_view.layoutManager = GridLayoutManager(context, 3)
    }
}
