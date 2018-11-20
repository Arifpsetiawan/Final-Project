package com.dicoding.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchItem (
    @SerializedName("idEvent")
    var idEvent: String? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null,

    @SerializedName("strHomeTeam")
    var strHomeTeam: String? = null,

    @SerializedName("intHomeScore")
    var intHomeScore: String? = null,

    @SerializedName("strHomeGoalDetails")
    val strHomeGoalDetails: String? = null,

    @SerializedName("strAwayTeam")
    var strAwayTeam: String? = null,

    @SerializedName("intAwayScore")
    var intAwayScore: String? = null,

    @SerializedName("strAwayGoalDetails")
    val strAwayGoalDetails: String? = null,

    @SerializedName("strTime")
    var strTime: String? = null
): Parcelable