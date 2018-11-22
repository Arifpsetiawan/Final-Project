package com.dicoding.footballapps.presenter

import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.api.TheSportDBApi
import com.dicoding.footballapps.model.TeamItemResponse
import com.dicoding.footballapps.utils.CoroutineContextProvider
import com.dicoding.footballapps.view.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter(private val view: TeamView,
                    private val apiRequest: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {


    fun getTeamListData(idLeague: String?) {
        view.showloading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRequest.doRequest(TheSportDBApi.getAllTeam(idLeague)).await(),
                    TeamItemResponse::class.java)

            view.showTeamListData(data.teams)
            view.hideloading()
        }
    }

    fun getSearchTeamData(idTeamBadge: String?) {
        view.showloading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRequest.doRequest(TheSportDBApi.getSearchTeam(idTeamBadge)).await(),
                    TeamItemResponse::class.java)

            view.showTeamListData(data.teams)
            view.hideloading()


        }
    }
}