package com.dicoding.footballapps.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dicoding.footballapps.fragment.OverviewTeamFragment
import com.dicoding.footballapps.fragment.PlayerFragment

class TeamDetailAdapter(fm: FragmentManager, private val descTeam: String?, private val idTeam: String?): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when(position) {
            0 -> OverviewTeamFragment()
            1 -> PlayerFragment()
            else -> null
        }
    }
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Overview Team"
            1 -> "Team Players"
            else -> null
        }
    }
}