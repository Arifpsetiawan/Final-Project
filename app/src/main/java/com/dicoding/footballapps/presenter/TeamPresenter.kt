package com.dicoding.footballapps.presenter

import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.api.TheSportDBApi
import com.dicoding.footballapps.model.TeamItemResponse
import com.dicoding.footballapps.utils.CoroutineContextProvider
import com.dicoding.footballapps.view.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(private val view: TeamView,
                    private val apiRequest: ApiRepository,
                    private val gson: Gson,
                    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getTeamListData(idLeague: String?) {
        view.showloading()

        async(contextProvider.main) {
            val data = bg {
                gson.fromJson(apiRequest.doRequest(TheSportDBApi.getAllTeam(idLeague)),
                    TeamItemResponse::class.java)
            }
            view.showTeamListData(data.await().teams)
            view.hideloading()
        }
    }

    fun getSearchTeamData(idTeamBadge: String?) {
        view.showloading()

        async(contextProvider.main) {
            val data = bg {
                gson.fromJson(apiRequest.doRequest(TheSportDBApi.getSearchTeam(idTeamBadge)),
                    TeamItemResponse::class.java)
            }
            view.showTeamListData(data.await().teams)
            view.hideloading()


        }
    }
}