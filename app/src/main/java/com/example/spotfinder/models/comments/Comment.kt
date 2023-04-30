package com.example.spotfinder.models.comments

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("user") val user: User
)