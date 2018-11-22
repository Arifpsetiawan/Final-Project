package com.dicoding.footballapps.presenter

import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.api.TheSportDBApi
import com.dicoding.footballapps.model.EventResponse
import com.dicoding.footballapps.view.SearchMatchView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMatchPresenter(private val view: SearchMatchView,
                           private val apiRequest: ApiRepository,
                           private val gson: Gson) {

    fun getSearchEvent(idSearch: String?) {
        view.showProgress()
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRequest
                .doRequest(TheSportDBApi.getSearchMatch(idSearch)),
                    EventResponse::class.java )

            view.showListEvent(data.await().event)
            view.hideProgress()
        }
    }
}