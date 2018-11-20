package com.dicoding.footballapps.view

import com.dicoding.footballapps.model.MatchItem

interface MatchEventView {

    fun showProgress()

    fun hideProgress()

    fun showDataMatchList(data: List<MatchItem>)
}