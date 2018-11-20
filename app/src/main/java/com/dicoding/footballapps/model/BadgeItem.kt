package com.dicoding.footballapps.model

import com.google.gson.annotations.SerializedName


data class BadgeItem (
    @SerializedName("strTeamBadge")
    val strTeamBadge: String? = ""
)