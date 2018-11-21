package com.dicoding.footballapps.presenter

import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.api.TheSportDBApi
import com.dicoding.footballapps.model.EventResponse
import com.dicoding.footballapps.utils.CoroutineContextProvider
import com.dicoding.footballapps.view.SearchMatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SearchMatchPresenter(private val view: SearchMatchView,
                           private val apiRequest: ApiRepository,
                           private val gson: Gson,
                           private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getSearchEvent(idSearch: String?) {
        view.showProgress()
        GlobalScope.async(contextProvider.main) {
            val data = bg {
                gson.fromJson(apiRequest.doRequest(TheSportDBApi.getSearchMatch(idSearch)),
                    EventResponse::class.java )
            }
            view.showListEvent(data.await().event)
            view.hideProgress()
        }
    }
}