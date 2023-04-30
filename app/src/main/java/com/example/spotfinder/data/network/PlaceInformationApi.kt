package com.example.spotfinder.data.network

import com.example.spotfinder.models.Ditto
import com.example.spotfinder.models.ResponsePlaces
import com.example.spotfinder.models.comments.ResponseComments
import com.example.spotfinder.models.login.LoginPostRequest
import com.example.spotfinder.models.login.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface PlaceInformationApi {

    @GET("lugares/")
    suspend fun getPlaces(
        @QueryMap queries: Map<String, String>
    ): Response<ResponsePlaces>

    @GET("comments/{id}")
    suspend fun getComments(@Path("id") idPlace: String): Response<ResponseComments>

    @POST("auth/login")
    suspend fun getLogin(@Body user: LoginPostRequest): Response<UserResponse>

    /*@GET("lugares")
    suspend fun getPlaces(): Response<ResponsePlaces>*/

}