package com.example.spotfinder.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Clasification (
    @SerializedName("family") val family:Boolean,
    @SerializedName("friends") val friends:Boolean,
    @SerializedName("couple") val couple:Boolean,
    @SerializedName("business") val business:Boolean,
    @SerializedName("single") val single:Boolean,
    @SerializedName("kids") val kids:Boolean,
    @SerializedName("others") val others:Boolean
): Parcelable