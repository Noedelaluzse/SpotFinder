package com.example.spotfinder.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.spotfinder.models.ResponsePlaces
import com.example.spotfinder.util.Constants.Companion.PLACES_TABLE

@Entity(tableName = PLACES_TABLE)
class PlacesEntity (
    var responsePlaces: ResponsePlaces
        ){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}