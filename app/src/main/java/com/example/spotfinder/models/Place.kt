package com.example.spotfinder.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Place (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: @RawValue Address,
    @SerializedName("uidOwner") val uidOwner: String,
    @SerializedName("restaurant") val restaurant:Boolean,
    @SerializedName("bar") val bar:Boolean,
    @SerializedName("cafe") val cafe:Boolean,
    @SerializedName("nightclub")val nightclub:Boolean,
    @SerializedName("live_music")val liveMusic:Boolean,
    @SerializedName("sports_bar")val sportBar:Boolean,
    @SerializedName("park") val park:Boolean,
    @SerializedName("comments") val comments: String,
    @SerializedName("img") val img: String

    ): Parcelable