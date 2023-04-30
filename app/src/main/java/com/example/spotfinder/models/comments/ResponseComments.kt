package com.example.spotfinder.models.comments

import com.google.gson.annotations.SerializedName

data class ResponseComments (
    @SerializedName("comment") val comment: List<Comment>,
    @SerializedName("commmetCount") val commmetCount: Int,
    @SerializedName("msg") val msg: String,
    @SerializedName("ok") val ok: Boolean
)