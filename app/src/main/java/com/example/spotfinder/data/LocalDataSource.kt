package com.example.spotfinder.data

import com.example.spotfinder.data.database.PlacesDao
import com.example.spotfinder.data.database.PlacesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val placesDao: PlacesDao
) {

    fun readDatabase(): Flow<List<PlacesEntity>> {
        return placesDao.readPlaces()
    }
    suspend fun insertPlaces(placesEntity: PlacesEntity) {
        placesDao.insertPlaces(placesEntity)
    }
}