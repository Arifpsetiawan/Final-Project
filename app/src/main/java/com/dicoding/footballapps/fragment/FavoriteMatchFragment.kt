package com.dicoding.footballapps.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.footballapps.R
import com.dicoding.footballapps.activity.activityDetail.ActivityDetailMatch
import com.dicoding.footballapps.adapter.FavoriteMatchAdapter
import com.dicoding.footballapps.database.MatchFavDB
import com.dicoding.footballapps.database.database
import kotlinx.android.synthetic.main.fragment_favmatch.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteMatchFragment: Fragment() {

    private var favoriteMatch: MutableList<MatchFavDB> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var listMatch: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteMatchAdapter(favoriteMatch) {
            ctx.startActivity<ActivityDetailMatch>(
                "id_event" to "${it.idEvent}",
                "home_team" to "${it.homeTeam}",
                "home_score" to "${it.homeScore}",
                "home_score" to "${it.homeScore}",
                "away_team" to "${it.awayTeam}",
                "away_score" to "${it.awayScore}",
                "away_score" to "${it.awayScore}",
                "date_event" to "${it.dateEvent}",
                "time_event" to "${it.time}"
            )
        }

        listMatch = recyclerView_favMatch
        listMatch.layoutManager = LinearLayoutManager(ctx)

        listMatch.adapter = adapter
        showFavoriteMatch()

        swipeRefresh_favorite_match.onRefresh {
            favoriteMatch.clear()
            showFavoriteMatch()
        }

    }

    private fun showFavoriteMatch() {
        context?.database?.use {
            swipeRefresh_favorite_match.isRefreshing = false
            val result = select(MatchFavDB.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<MatchFavDB>())
            favoriteMatch.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favmatch, container, false)
    }
}