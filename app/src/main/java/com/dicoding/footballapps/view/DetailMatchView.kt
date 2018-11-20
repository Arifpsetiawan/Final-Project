package com.dicoding.footballapps.view

import android.widget.ProgressBar
import com.dicoding.footballapps.model.BadgeItem
import com.dicoding.footballapps.model.DetailMatchItem

interface DetailMatchView {

    fun showProgress(progressBar: ProgressBar)

    fun hideProgress(progressBar: ProgressBar)

    fun showDetailMatch (data : List<DetailMatchItem>)

    fun showHomeTeamBadge (data: List<BadgeItem>)

    fun showAwayTeamBadge (data: List<BadgeItem>)

}