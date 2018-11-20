package com.dicoding.footballapps.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dicoding.footballapps.fragment.FavoriteMatchFragment
import com.dicoding.footballapps.fragment.FavoriteTeamFragment

class FavoriteAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when(position) {
            0 -> FavoriteMatchFragment()
            1 -> FavoriteTeamFragment()
            else -> null

        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Matches"
            1 -> "Teams"
            else -> null
        }
    }
}