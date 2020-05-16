package maw.mobet.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_my.*
import maw.mobet.R
import maw.mobet.api.MyItem
import maw.mobet.ui.my.finish.FragmentFinish
import maw.mobet.ui.my.playing.FragmentPlaying

class MyFragment : Fragment() {
    private lateinit var viewModel: MyViewModel
    private val fragmentPlaying = FragmentPlaying.newInstance()
    private val fragmentFinish = FragmentFinish.newInstance()
    private lateinit var myItem: MyItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view_pager.adapter = PagerAdapter(this, listOf(
            fragmentPlaying,
            fragmentFinish
        ))

        viewModel.list.observe(viewLifecycleOwner, Observer {
            myItem = it
        })
        viewModel.loadData()
    }
}
