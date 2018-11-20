package com.dicoding.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamItem(
    val id: Long?,

    @SerializedName("idTeam")
    var idTeam: String? = null,

    @SerializedName("strTeam")
    var strTeam: String? = null,

    @SerializedName("strTeamBadge")
    var strTeamBadge: String? = null,

    @SerializedName("intFormedYear")
    var intFormedYear: String? = null,

    @SerializedName("strStadium")
    var strStadium: String? = null,

    @SerializedName("strStadiumThumb")
    var strStadiumThumb: String? = null,

    @SerializedName("strTeamFanart1")
    var strTeamFanart1: String? = null,

    @SerializedName("strTeamFanart2")
    var strTeamFanart2: String? = null,

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = null
) : Parcelable