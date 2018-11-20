package com.dicoding.footballapps.view

import com.dicoding.footballapps.model.EventItem

interface SearchMatchView {
    fun showProgress()
    fun hideProgress()
    fun showListEvent(event: List<EventItem>)
}