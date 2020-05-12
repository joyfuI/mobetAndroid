package maw.mobet.ui.game.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import maw.mobet.Game2Activity
import maw.mobet.R
import maw.mobet.ui.game.GameViewModel

class InfoFragment : Fragment() {
    companion object {
        fun newInstance() = InfoFragment()
    }

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as Game2Activity)[GameViewModel::class.java]
        return inflater.inflate(R.layout.fragment_game_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
