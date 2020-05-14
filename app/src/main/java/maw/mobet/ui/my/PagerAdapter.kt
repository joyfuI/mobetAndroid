package maw.mobet.ui.my

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    fragment: Fragment, private val list: List<Fragment>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]
}