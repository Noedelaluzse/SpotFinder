package com.example.spotfinder.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spotfinder.models.ResponsePlaces
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertPlaces(placesEntity: PlacesEntity)

    @Query("SELECT * FROM places_table ORDER BY id ASC")
    fun readPlaces(): Flow<List<PlacesEntity>>

}