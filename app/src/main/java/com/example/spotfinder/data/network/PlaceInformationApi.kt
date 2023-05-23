package com.example.spotfinder.data.network

import com.example.spotfinder.models.Ditto
import com.example.spotfinder.models.ResponsePlaces
import com.example.spotfinder.models.UserLogged
import com.example.spotfinder.models.WrapperUser
import com.example.spotfinder.models.comments.NewComment
import com.example.spotfinder.models.comments.ResponseComments
import com.example.spotfinder.models.login.LoginPostRequest
import com.example.spotfinder.models.login.NewUser
import com.example.spotfinder.models.login.UserResponse
import okhttp3.ResponseBody
import org.json.JSONObject
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

    @GET("auth/user/{id}")
    suspend fun getUserInfo(@Path("id") idUser: String): Response<WrapperUser>

    @POST("auth/new")
    suspend fun createUser(@Body newUser: NewUser): Response<UserResponse>

    @POST("comments")
    suspend fun postNewComment(@Body newComment: NewComment): Response<ResponseComments>


    /*@GET("lugares")
    suspend fun getPlaces(): Response<ResponsePlaces>*/

}