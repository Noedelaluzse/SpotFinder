package com.example.spotfinder.models.comments

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("img") val img: String?
)