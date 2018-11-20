package com.dicoding.footballapps.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dicoding.footballapps.fragment.NextMatchFragment
import com.dicoding.footballapps.fragment.PastMatchFragment

class MatchAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? = when(position) {
        0 -> PastMatchFragment()
        1 -> NextMatchFragment()
        else -> null
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Past Match"
            1 -> "Next Match"
            else -> null
        }
    }
}