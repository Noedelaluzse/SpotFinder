package com.example.spotfinder.models.login

data class NewUser(
    val name:String,
    val phone:String,
    val password:String,
    val img:String,
    val owner:Boolean,
    val status:Boolean,
    val google:Boolean
)