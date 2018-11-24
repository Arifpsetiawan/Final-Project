package com.dicoding.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlayerItem(

    @SerializedName("idTeam")
    var idTeam: String? = null,

    @SerializedName("strPlayer")
    var strPlayer: String? = null,

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = null,

    @SerializedName("strPosition")
    var strPosition: String? = null,

    @SerializedName("strWeight")
    var strWeight: String? = null,

    @SerializedName("strHeight")
    var strHeight: String? = null,

    @SerializedName("strCutout")
    var strCutout: String? = null,

    @SerializedName("strFanart1")
    var strFanart1: String? = null
) : Serializable