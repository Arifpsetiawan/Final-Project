package com.dicoding.footballapps.presenter

import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.api.TheSportDBApi
import com.dicoding.footballapps.model.PlayerItemResponse
import com.dicoding.footballapps.view.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(private val view: PlayerView,
                      private val apiRequest: ApiRepository,
                      private val gson: Gson){

    fun getPlayerListData(id: String?) {
        view.showProgress()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRequest
                .doRequest(TheSportDBApi.getAllPlayer(id)).await(),
                    PlayerItemResponse::class.java)

            view.showPlayerListData(data.player)
            view.hideProgress()
        }
    }
}