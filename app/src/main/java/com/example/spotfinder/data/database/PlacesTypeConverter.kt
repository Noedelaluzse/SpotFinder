package com.example.spotfinder.data.database

import androidx.room.TypeConverter
import com.example.spotfinder.models.ResponsePlaces
import com.example.spotfinder.models.login.UserResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PlacesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun responsePlacesToString(responsePlaces: ResponsePlaces): String {
        return gson.toJson(responsePlaces)
    }

    @TypeConverter
    fun stringToResponsePlaces(data: String) : ResponsePlaces {
        val listType = object : TypeToken<ResponsePlaces>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun responseUserToString(responseUser: UserResponse): String {
        return gson.toJson(responseUser)
    }

    @TypeConverter
    fun stringToResponseUser(data: String) : UserResponse {
        val listType = object : TypeToken<UserResponse>() {}.type
        return gson.fromJson(data, listType)
    }
}