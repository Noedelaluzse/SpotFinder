package com.example.spotfinder.data

import com.example.spotfinder.data.database.PlacesDao
import com.example.spotfinder.data.database.PlacesEntity
import com.example.spotfinder.data.database.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val placesDao: PlacesDao,
) {

    fun readDatabase(): Flow<List<PlacesEntity>> {
        return placesDao.readPlaces()
    }
    /*

    fun readUser(): Flow<List<UserEntity>> {
        return placesDao.readUser()
    }*/
    suspend fun insertPlaces(placesEntity: PlacesEntity) {
        placesDao.insertPlaces(placesEntity)
    }

    /*
    suspend fun insertUser(userEntity: UserEntity) {
        placesDao.inserUser(userEntity)
    }
    */
    suspend fun deleteDatabase() {
        return placesDao.deleteAll()
    }

}