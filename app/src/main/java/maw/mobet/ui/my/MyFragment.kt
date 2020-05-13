package maw.mobet.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.fragment_my.*
import maw.mobet.R
import maw.mobet.ui.my.achievement.Fragment_finish
import maw.mobet.ui.my.statistics.Fragment_playing
import androidx.fragment.app.FragmentManager as FragmentManager

class MyFragment : Fragment() {
    private lateinit var viewModel: MyViewModel
    private val fragmentPlaying = Fragment_playing.newInstance()
    private val fragmentFinish = Fragment_finish.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        view_pager.adapter = PagerAdapter(this, listOf(
            fragmentPlaying,
            fragmentFinish
        ))

        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}
