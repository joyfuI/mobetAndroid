package maw.mobet.ui.my.finish

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.fragment_my_finish.*
import maw.mobet.DialogActivity
import maw.mobet.GameActivity
import maw.mobet.R
import maw.mobet.RankActivity
import maw.mobet.api.GameItem
import maw.mobet.api.MyItem
import maw.mobet.ui.my.MyFragment
import maw.mobet.ui.my.MyViewModel
import splitties.fragments.start

class FragmentFinish : Fragment(), MyfinishAdapter.OnItemClickListener {
    companion object {
        fun newInstance() = FragmentFinish()
    }

    private lateinit var viewModel: MyViewModel
    private lateinit var listDate: List<MyItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(parentFragment as MyFragment)[MyViewModel::class.java]
        return inflater.inflate(R.layout.fragment_my_finish, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_finish.layoutManager = LinearLayoutManager(activity)
        viewModel.list.observe(viewLifecycleOwner, Observer {
            rv_finish.adapter = MyfinishAdapter(it.ending,this)
        })

    }
    override fun onItemClick(view: View, position: Int) {
        val item = view.tag as GameItem

        start<DialogActivity> {
            putExtra("id", item.id)
            putExtra("data", item)


        }
    }
}