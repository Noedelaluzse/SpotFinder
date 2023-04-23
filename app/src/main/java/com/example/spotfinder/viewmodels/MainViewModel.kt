package com.example.spotfinder.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.spotfinder.data.Repository
import com.example.spotfinder.data.database.PlacesEntity
import com.example.spotfinder.models.Ditto
import com.example.spotfinder.models.ResponsePlaces
import com.example.spotfinder.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor ( private val repository: Repository,
    application: Application): AndroidViewModel(application) {


    /** ROOM DATABASE **/
    val readPlaces: LiveData<List<PlacesEntity>> = repository.local.readDatabase().asLiveData()

    private fun insertPlaces(placesEntity: PlacesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertPlaces(placesEntity)
        }

    /** RETROFIT **/
    var placesResponse: MutableLiveData<NetworkResult<ResponsePlaces>> = MutableLiveData()

    fun getPlaces(queries: Map<String, String>) = viewModelScope.launch  {
        getPlacesSaveCall(queries)
    }

    private suspend fun getPlacesSaveCall(queries: Map<String, String>) {
        placesResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()) {
            try {
                val response = repository.remote.getListPlaces(queries)
                placesResponse.value = hadlePlacesResponse(response)

                val offlineData = placesResponse.value!!.data
                if (offlineData != null ) {
                    offlineCachePlaces(offlineData)
                }
            } catch (e: Exception) {
                placesResponse.value = NetworkResult.Error("Lugares no encontrados.")
            }
        } else {
            placesResponse.value = NetworkResult.Error("Sin conexión a intenet.")
        }

    }

    private fun offlineCachePlaces(offlineData: ResponsePlaces) {
        val placesEntity = PlacesEntity(offlineData)
        insertPlaces(placesEntity)
    }


    private fun hadlePlacesResponse(response: Response<ResponsePlaces>): NetworkResult<ResponsePlaces>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key limited.")
            }
            response.body()!!.arrLugares.isNullOrEmpty() -> {
                return NetworkResult.Error(response.message())
            }
            response.isSuccessful -> {
                val places = response.body()
                return NetworkResult.Success(places!!)
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