package com.dicoding.footballapps.presenter

import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.api.TheSportDBApi
import com.dicoding.footballapps.model.PlayerItemResponse
import com.dicoding.footballapps.utils.CoroutineContextProvider
import com.dicoding.footballapps.view.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView,
                      private val apiRequest: ApiRepository,
                      private val gson: Gson,
                      private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()){

    fun getPlayerListData(id: String?) {
        view.showProgress()

        GlobalScope.async(contextProvider.main) {
            val data = bg {
                gson.fromJson(apiRequest.doRequest(TheSportDBApi.getAllPlayer(id)),
                    PlayerItemResponse::class.java)
            }
            view.showPlayerListData(data.await().player)
            view.hideProgress()
        }
    }
}