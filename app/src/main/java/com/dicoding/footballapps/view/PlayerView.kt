package com.dicoding.footballapps.view

import com.dicoding.footballapps.model.PlayerItem

interface PlayerView {
    fun showProgress()
    fun hideProgress()
    fun showPlayerListData(data: List<PlayerItem>)
}