package com.example.spotfinder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PlacesEntity::class],
    version = 1,
)
@TypeConverters(PlacesTypeConverter::class)
abstract class PlacesDatabase: RoomDatabase() {

    abstract fun placesDao(): PlacesDao
}