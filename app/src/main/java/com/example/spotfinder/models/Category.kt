package com.example.spotfinder.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    @SerializedName("restaurant") val restaurant: Boolean,
    @SerializedName("bar") val bar: Boolean,
    @SerializedName("cafe") val cafe: Boolean,
    @SerializedName("nightclub") val nightclub: Boolean,
    @SerializedName("live_music") val live_music: Boolean,
    @SerializedName("sports_bar") val sports_bar: Boolean,
    @SerializedName("park") val park: Boolean
    ) : Parcelable
