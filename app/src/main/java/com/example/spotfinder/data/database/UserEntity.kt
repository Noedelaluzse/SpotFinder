package com.example.spotfinder.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.spotfinder.models.login.UserResponse
import com.example.spotfinder.util.Constants.Companion.USER_TABLE

@Entity(tableName = USER_TABLE)
class UserEntity(
    var userResponse: UserResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}