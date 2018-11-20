package com.dicoding.footballapps.view

import com.dicoding.footballapps.model.TeamItem

interface TeamView {
    fun showloading()
    fun hideloading()
    fun showTeamListData(data: List<TeamItem>)
}