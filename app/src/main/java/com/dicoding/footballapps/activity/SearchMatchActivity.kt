package com.dicoding.footballapps.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.dicoding.footballapps.R
import com.dicoding.footballapps.activity.activityDetail.ActivityDetailMatch
import com.dicoding.footballapps.adapter.SearchMatchAdapter
import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.model.EventItem
import com.dicoding.footballapps.model.MatchItem
import com.dicoding.footballapps.presenter.SearchMatchPresenter
import com.dicoding.footballapps.view.SearchMatchView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private var eventDataItem: MutableList<EventItem> = mutableListOf()
    private var extraDataEvent: MutableList<MatchItem> = mutableListOf()

    private lateinit var presenter: SearchMatchPresenter
    private lateinit var adapter: SearchMatchAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.hide()

        searchView_match.onActionViewExpanded()
        searchView_match.onQueryTextListener() {
            onQueryTextChange { it ->
                presenter.getSearchEvent(it)
                true
            }
            hideProgress()
        }

        val apiRequest = ApiRepository()
        val gson = Gson()
        presenter = SearchMatchPresenter(this,apiRequest,gson)

        adapter = SearchMatchAdapter(eventDataItem) {
            extraDataEvent.clear()
            extraDataEvent.add(MatchItem(
                it.idEvent,
                it.dateEvent,
                it.strHomeTeam,
                it.intHomeScore,
                "",
                it.strAwayTeam,
                it.intAwayScore,
                "",
                it.strTime))
            ctx.startActivity<ActivityDetailMatch>(
                "id_event" to extraDataEvent[0].idEvent,
                "home_team" to extraDataEvent[0].strHomeTeam,
                "home_score" to extraDataEvent[0].intHomeScore,
                "away_team" to extraDataEvent[0].strAwayTeam,
                "away_score" to extraDataEvent[0].intAwayScore,
                "date_event" to extraDataEvent[0].dateEvent,
                "time_event" to extraDataEvent[0].strTime
            )
        }

        recyclerView = recyclerView_match_search
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefresh_match_search.onRefresh {
            presenter.getSearchEvent("Barcelona")
            hideProgress()
        }
    }

    override fun showProgress() {
        progressBar_match_search.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar_match_search.visibility = View.GONE
    }

    override fun showListEvent(event: List<EventItem>) {

        eventDataItem.clear()
        eventDataItem.addAll(event)
        adapter.notifyDataSetChanged()
        hideProgress()
    }
}