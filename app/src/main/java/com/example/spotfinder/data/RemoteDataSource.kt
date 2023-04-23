package com.example.spotfinder.data

import android.util.Log
import com.example.spotfinder.data.network.PlaceInformationApi
import com.example.spotfinder.models.Ditto
import com.example.spotfinder.models.ResponsePlaces
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val placeInformationApi: PlaceInformationApi
) {
    suspend fun getListPlaces(queries: Map<String, String>): Response<ResponsePlaces> {
        return placeInformationApi.getPlaces(queries)
    }

}