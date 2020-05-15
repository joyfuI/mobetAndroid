package maw.mobet.ui.my.playing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_my_playing.*
import maw.mobet.GameActivity
import maw.mobet.R
import maw.mobet.api.GameItem
import maw.mobet.api.MyItem
import maw.mobet.ui.my.MyFragment
import maw.mobet.ui.my.MyViewModel
import splitties.fragments.start

class FragmentPlaying  : Fragment(), MyplayingAdapter.OnClickListener {
    companion object {
        fun newInstance() = FragmentPlaying()
    }

    private lateinit var viewModel: MyViewModel
    private lateinit var listDate: List<MyItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(parentFragment as MyFragment)[MyViewModel::class.java]
        return inflater.inflate(R.layout.fragment_my_playing, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_playing.layoutManager = LinearLayoutManager(activity)
        viewModel.list.observe(viewLifecycleOwner, Observer {
            Log.d("joyfuI", it.playing.size.toString())
            rv_playing.adapter = MyplayingAdapter(it.playing, this)
        })
    }
    override fun onClick(view: View, position: Int) {
        val item = view.tag as GameItem

        start<GameActivity> {
            putExtra("id", item.id)
            putExtra("data", item)
        }
    }
}