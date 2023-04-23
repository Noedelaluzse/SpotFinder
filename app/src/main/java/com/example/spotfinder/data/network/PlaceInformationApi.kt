package com.example.spotfinder.data.network

import com.example.spotfinder.models.Ditto
import com.example.spotfinder.models.ResponsePlaces
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PlaceInformationApi {

    @GET("lugares/")
    suspend fun getPlaces(
        @QueryMap queries: Map<String, String>
    ): Response<ResponsePlaces>

    /*@GET("lugares")
    suspend fun getPlaces(): Response<ResponsePlaces>*/

}