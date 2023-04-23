package com.example.spotfinder.models

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("street") val street: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("zip") val zip: String
)