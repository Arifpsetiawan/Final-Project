package com.dicoding.footballapps.presenter

import com.dicoding.footballapps.api.ApiRepository
import com.dicoding.footballapps.api.TheSportDBApi
import com.dicoding.footballapps.model.TeamItem
import com.dicoding.footballapps.model.TeamItemResponse
import com.dicoding.footballapps.utils.TestContextProvider
import com.dicoding.footballapps.view.TeamView
import com.google.gson.Gson
import org.junit.Test
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TeamPresenterTest {
    @Mock
    private lateinit var teamView: TeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(teamView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamListData() {
        val teams: MutableList<TeamItem> = mutableListOf()
        val response = TeamItemResponse(teams)
        val league = "English Premiere League"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getAllTeam(league)).await(),
                TeamItemResponse::class.java
            )).thenReturn(response)

            presenter.getTeamListData(league)

            Mockito.verify(teamView).showloading()
            Mockito.verify(teamView).showTeamListData(teams)
            Mockito.verify(teamView).hideloading()
        }
    }

    @Test
    fun getSearchTeamData() {
        val teams: MutableList<TeamItem> = mutableListOf()
        val response = TeamItemResponse(teams)
        val league = "English Premiere League"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getSearchTeam(league)).await(),
                TeamItemResponse::class.java
            )).thenReturn(response)

            presenter.getSearchTeamData(league)

            Mockito.verify(teamView).showloading()
            Mockito.verify(teamView).showTeamListData(teams)
            Mockito.verify(teamView).hideloading()
        }
    }
}