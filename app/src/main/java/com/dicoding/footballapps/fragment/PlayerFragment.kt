package com.dicoding.footballapps.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.dicoding.footballapps.R
import com.dicoding.footballapps.activity.activityDetail.ActivityDetailPlayer
import com.dicoding.footballapps.adapter.PlayerAdapter
import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.model.PlayerItem
import com.dicoding.footballapps.model.TeamItem
import com.dicoding.footballapps.presenter.PlayerPresenter
import com.dicoding.footballapps.view.PlayerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class PlayerFragment : Fragment(), PlayerView {

    private var id: String? = null
    private lateinit var teamData: TeamItem
    private var playerData: MutableList<PlayerItem> = mutableListOf()
    private lateinit var recyclerViewPlayer: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: PlayerAdapter
    private lateinit var presenter : PlayerPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        teamData = activity?.intent!!.getParcelableExtra("team_data")
        id = teamData.idTeam
        Log.d("debug","Id Team : " +id)

        progressBar = progressBar_player
        swipeRefreshLayout = swipeRefresh_player

        adapter = PlayerAdapter(playerData) {

            ctx.startActivity<ActivityDetailPlayer>("player_data" to it )
        }

        recyclerViewPlayer = recyclerView_player
        recyclerViewPlayer.layoutManager = LinearLayoutManager(ctx)

        recyclerViewPlayer.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)

        presenter.getPlayerListData(id)

        swipeRefreshLayout.onRefresh {
            presenter.getPlayerListData(id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showPlayerListData(data: List<PlayerItem>) {
        swipeRefreshLayout.isRefreshing = false
        playerData.clear()
        playerData.addAll(data)
        adapter.notifyDataSetChanged()
        hideProgress()
    }
}