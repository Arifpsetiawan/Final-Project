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
import com.dicoding.footballapps.activity.activityDetail.ActivityDetailTeam
import com.dicoding.footballapps.adapter.FavoriteTeamAdapter
import com.dicoding.footballapps.database.TeamFavDB
import com.dicoding.footballapps.database.database
import com.dicoding.footballapps.model.TeamItem
import kotlinx.android.synthetic.main.fragment_favteam.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteTeamFragment : Fragment() {

    private var favoriteTeam : MutableList<TeamItem> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter
    private lateinit var rvTeam : RecyclerView
    private lateinit var swipeRefresh : SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteTeamAdapter(favoriteTeam){
            ctx.startActivity<ActivityDetailTeam>("team_data" to it)
        }

        rvTeam = recyclerView_favTeam
        rvTeam.layoutManager = LinearLayoutManager(ctx)
        rvTeam.adapter = adapter
        showFavoriteTeam()

        swipeRefresh_favorite_team.onRefresh {
            favoriteTeam.clear()
            showFavoriteTeam()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favteam, container, false)
    }

    private fun showFavoriteTeam() {
        context?.database?.use {
            swipeRefresh_favorite_team.isRefreshing = false
            val result = select(TeamFavDB.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<TeamItem>())
            favoriteTeam.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}