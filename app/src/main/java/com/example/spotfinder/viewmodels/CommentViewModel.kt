package com.example.spotfinder.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spotfinder.data.Repository
import com.example.spotfinder.models.comments.ResponseComments
import com.example.spotfinder.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor (
    application: Application,
    private val repository: Repository,
): AndroidViewModel(application) {

    var commentsResponse: MutableLiveData<NetworkResult<ResponseComments>> = MutableLiveData()
    var networkStatus = false

    fun getComments(idPlace: String) = viewModelScope.launch {
        getCommentsSaveCall(idPlace)
    }

    private suspend fun getCommentsSaveCall(idPlace: String) {
        commentsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getListComment(idPlace)
                commentsResponse.value = handleCommentsResponse(response)
            } catch (e: Exception) {
                commentsResponse.value = NetworkResult.Error("Comentarios no encontrados")
            }
        } else {
            commentsResponse.value = NetworkResult.Error("Sin conexión a internet")
        }
    }
    private fun handleCommentsResponse(response: Response<ResponseComments>): NetworkResult<ResponseComments>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key limited.")
            }
            response.body()!!.comment.isNullOrEmpty() -> {
                return NetworkResult.Error(response.message())
            }
            response.isSuccessful -> {
                val comments = response.body()
                return NetworkResult.Success(comments!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}