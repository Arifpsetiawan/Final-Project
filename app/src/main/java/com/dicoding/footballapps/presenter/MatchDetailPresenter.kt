package com.dicoding.footballapps.presenter

import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.api.TheSportDBApi
import com.dicoding.footballapps.model.BadgeResponse
import com.dicoding.footballapps.model.DetailMatchResponse
import com.dicoding.footballapps.view.DetailMatchView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Deferred

class MatchDetailPresenter (private val detailMatchEventView: DetailMatchView,
                            private val apiRequest: ApiRepository,
                            private val gson: Gson) {

    fun getTeamBadge( team: String?, teamType: String? ){

        GlobalScope.launch(Dispatchers.Main) {
            val dataTeam = gson.fromJson(apiRequest
                .doRequest(TheSportDBApi.getBadgeTeam(team)),
                BadgeResponse::class.java)

            if(teamType == "Away")
                detailMatchEventView.showAwayTeamBadge(dataTeam.teams)
            else
                detailMatchEventView.showHomeTeamBadge(dataTeam.teams)

        }
    }

    fun getMatchEventDetail(idMatchEvent: String?){

        GlobalScope.launch(Dispatchers.Main) {
            val dataDetail =
                gson.fromJson(apiRequest.doRequest(
                    TheSportDBApi.getDetailMatchEvent(idMatchEvent)),
                    DetailMatchResponse::class.java)

            detailMatchEventView.showDetailMatch(dataDetail.await().events)
        }
    }
}