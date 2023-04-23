package com.example.spotfinder.models

import com.google.gson.annotations.SerializedName

data class Ditto(
    @SerializedName("base_experience") val baseExperience: Int? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val ditto: String? = null)