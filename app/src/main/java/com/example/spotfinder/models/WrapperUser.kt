package com.example.spotfinder.models

import com.google.gson.annotations.SerializedName

data class WrapperUser(
    @SerializedName("ok") val ok: Boolean,
    @SerializedName("usuario") val user: UserLogged

)