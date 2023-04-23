package com.example.spotfinder.models

import com.google.gson.annotations.SerializedName

data class ResponsePlaces(
    @SerializedName("ok") val ok : Boolean,
    @SerializedName("msg")  val msg: String,
    @SerializedName("lugares") val arrLugares: List<Place>
)