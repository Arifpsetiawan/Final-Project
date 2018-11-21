package com.dicoding.footballapps.presenter

import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.api.TheSportDBApi
import com.dicoding.footballapps.model.MatchItemResponse
import com.dicoding.footballapps.utils.CoroutineContextProvider
import com.dicoding.footballapps.view.MatchEventView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter (private val matchEventView: MatchEventView,
                      private val apiRequest: ApiRepository,
                      private val gson: Gson
                      , private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getMatchPastData(idLeague: String?){
        GlobalScope.async(context.main) {
            val dataMatch = bg {
                gson.fromJson(apiRequest
                    .doRequest(TheSportDBApi.getPastMatchEvent(idLeague))
                    , MatchItemResponse::class.java
                )
            }

            matchEventView.showDataMatchList(dataMatch.await().events)
        }
    }

    fun getMatchNextData(idLeague: String?) {
        GlobalScope.async(context.main) {
            val dataMatch = bg {
                gson.fromJson( apiRequest.doRequest(TheSportDBApi.getNextMatchEvent(idLeague))
                    , MatchItemResponse::class.java )
            }
            matchEventView.showDataMatchList(dataMatch.await().events)
        }
    }
}