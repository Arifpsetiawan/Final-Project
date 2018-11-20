package com.dicoding.footballapps.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.dicoding.footballapps.R
import com.dicoding.footballapps.activity.activityDetail.ActivityDetailMatch
import com.dicoding.footballapps.fragment.*
import com.dicoding.footballapps.model.MatchItem
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(),
    PastMatchFragment.OnFragmentInteractionListener,
    NextMatchFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(item: MatchItem) {
        startActivity<ActivityDetailMatch>(
            "id_event" to item.idEvent
            ,   "date_event" to item.dateEvent
            ,   "home_team" to item.strHomeTeam
            ,   "home_score" to item.intHomeScore
            ,   "away_team" to item.strAwayTeam
            ,   "away_score" to item.intAwayScore )
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.match_menu -> {
                toast(R.string.match_nav)
                val fragment = MatchFragment()
                openMatchFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.team_menu -> {
                longToast(R.string.team_nav)
                val fragment = TeamFragment()
                openMatchFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.favorite_menu -> {
                longToast(R.string.favorite_nav)
                val fragment = FavoriteFragment()
                openMatchFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stetho.initializeWithDefaults(this)

        openMatchFragment( MatchFragment() )

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun openMatchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFrame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
