package com.example.spotfinder.models.comments

data class NewComment(
    val placeId: String,
    val text: String,
    val userId: String
)