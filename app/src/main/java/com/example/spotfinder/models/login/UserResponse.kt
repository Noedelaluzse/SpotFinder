package com.example.spotfinder.models.login

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("ok") val ok: Boolean,
    @SerializedName("uid") val uid: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("img") val img: String,
    @SerializedName("token") val token: String,
)