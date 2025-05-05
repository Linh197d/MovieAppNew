package com.qibla.muslimday.app2025.ui.intro

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.qibla.muslimday.app2025.base.BaseFragment

class IntroAdapter(
    private val fm: FragmentActivity,
    private val listFragment: List<BaseFragment<*>>
) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}