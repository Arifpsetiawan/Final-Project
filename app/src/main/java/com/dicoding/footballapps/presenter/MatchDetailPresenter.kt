package com.dicoding.footballapps.presenter

import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.api.TheSportDBApi
import com.dicoding.footballapps.model.BadgeResponse
import com.dicoding.footballapps.model.DetailMatchResponse
import com.dicoding.footballapps.utils.CoroutineContextProvider
import com.dicoding.footballapps.view.DetailMatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter (public val detailMatchEventView: DetailMatchView,
                            public val apiRequest: ApiRepository,
                            public val gson: Gson
                            , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamBadge( team: String?, teamType: String? ){

        doAsync {
            val dataTeam = gson.fromJson(apiRequest
                .doRequest(TheSportDBApi.getBadgeTeam(team))
                , BadgeResponse::class.java )

            uiThread {
                if(teamType == "Away")
                    detailMatchEventView.showAwayTeamBadge(dataTeam.teams)
                else
                    detailMatchEventView.showHomeTeamBadge(dataTeam.teams)

            }
        }
    }

    fun getMatchEventDetail(idMatchEvent: String?){

        async(context.main) {
            val dataDetail = bg {
                gson.fromJson(apiRequest.doRequest(
                    TheSportDBApi.getDetailMatchEvent(idMatchEvent))
                    , DetailMatchResponse::class.java)
            }
            detailMatchEventView.showDetailMatch(dataDetail.await().events)
        }
    }
}