package maw.mobet.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_my.*
import maw.mobet.R
import maw.mobet.RankActivity
import maw.mobet.api.MyItem
import maw.mobet.ui.my.finish.FragmentFinish
import maw.mobet.ui.my.playing.FragmentPlaying
import splitties.fragments.start

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

            Glide.with(this).load(myItem.my.imgUrl).into(profile_img)
            profile_txt.text = myItem.my.nick
            email_txt.text = FirebaseAuth.getInstance().currentUser?.email.toString()
        })
        viewModel.loadData()

        profile_l.setOnClickListener {
            start<RankActivity> {
                putExtra("id", myItem.my.nick)
            }
        }

        // RadioButton
        toggle.setOnCheckedChangeListener { _, i ->
            when (i) {
                // 진행중
                play_rdo.id -> view_pager.currentItem = 0
                // 전적
                end_rdo.id -> view_pager.currentItem = 1
            }
        }
        // ViewPager
        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                toggle.check(toggle[position].id)
            }
        })
    }
}
