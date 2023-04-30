package com.example.spotfinder.models.login

import com.google.gson.annotations.SerializedName

data class LoginPostRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String
)