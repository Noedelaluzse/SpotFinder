package com.example.spotfinder.data

import android.util.Log
import com.example.spotfinder.data.network.PlaceInformationApi
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
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val placeInformationApi: PlaceInformationApi
) {
    suspend fun getListPlaces(queries: Map<String, String>): Response<ResponsePlaces> {
        return placeInformationApi.getPlaces(queries)
    }

    suspend fun getListComment(idPlace: String): Response<ResponseComments> {
        return placeInformationApi.getComments(idPlace)
    }

    suspend fun getLogin(user: LoginPostRequest): Response<UserResponse> {
        return placeInformationApi.getLogin(user)
    }

    suspend fun getUserInfo(id: String): Response<WrapperUser> {
        return placeInformationApi.getUserInfo(id)
    }

    suspend fun createUer(newUser: NewUser) : Response<UserResponse> {
        return placeInformationApi.createUser(newUser)
    }

    suspend fun createComment(newComment: NewComment): Response<ResponseComments> {
        return placeInformationApi.postNewComment(newComment)
    }

}