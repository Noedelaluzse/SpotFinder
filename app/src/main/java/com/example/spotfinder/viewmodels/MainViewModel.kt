package com.example.spotfinder.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.spotfinder.data.DataStoreRepository
import com.example.spotfinder.data.Repository
import com.example.spotfinder.data.database.PlacesEntity
import com.example.spotfinder.models.ResponsePlaces
import com.example.spotfinder.models.UserLogged
import com.example.spotfinder.models.WrapperUser
import com.example.spotfinder.models.login.LoginPostRequest
import com.example.spotfinder.models.login.NewUser
import com.example.spotfinder.models.login.UserResponse
import com.example.spotfinder.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.spotfinder.util.Constants.Companion.LOG_OUT
import com.example.spotfinder.util.NetworkResult
import com.example.spotfinder.util.Uuid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MainViewModel @Inject constructor (
    private val repository: Repository,
    private val dataStoreRepository: DataStoreRepository,
    application: Application): AndroidViewModel(application) {

    private  var id = ""

    /** ROOM DATABASE **/
    val readPlaces: LiveData<List<PlacesEntity>> = repository.local.readDatabase().asLiveData()
    val readUid = dataStoreRepository.readUid
    val readJWT = dataStoreRepository.readJWT

    private fun insertPlaces(placesEntity: PlacesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertPlaces(placesEntity)
        }


    /** RETROFIT **/
    var placesResponse: MutableLiveData<NetworkResult<ResponsePlaces>> = MutableLiveData()
    var loginResponse: MutableLiveData<NetworkResult<UserResponse>> = MutableLiveData()
    var userInfo: MutableLiveData<NetworkResult<WrapperUser>> = MutableLiveData()

    val temp: MutableLiveData<String> = MutableLiveData("")
    val jwtUser: MutableLiveData<String> = MutableLiveData("")


    fun getPlaces(queries: Map<String, String>) = viewModelScope.launch  {
        getPlacesSaveCall(queries)
    }

    fun getLogin(user: LoginPostRequest) = viewModelScope.launch {
        getLoginSaveCall(user)
    }

    fun createUser(newUser: NewUser) = viewModelScope.launch {
        createNewUserSaveCall(newUser)
    }
    fun getUserInfo(id: String) = viewModelScope.launch {
        getUserInfoSaveCall(id)
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
    private suspend fun getUserInfoSaveCall(id: String) {
        userInfo.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getUserInfo(id)
                userInfo.value = handleUserInfoResponse(response)
                val userInfo = userInfo.value!!.data
            } catch (e: Exception) {
                loginResponse.value = NetworkResult.Error("Usuario no encontrado")
            }
        } else {
            loginResponse.value = NetworkResult.Error("Sin conexión a internet")
        }

    }

    private suspend fun getLoginSaveCall(user: LoginPostRequest) {
        loginResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getLogin(user)
                loginResponse.value = handleLoginResponse(response)
                val userInfo = loginResponse.value!!.data
                if (userInfo != null) {
                    Log.d("savingData",userInfo.toString())
                    dataStoreRepository.saveUID(userInfo.uid)
                    dataStoreRepository.saveJWT(userInfo.token)
                    Uuid.UUID_SAVE = userInfo.token
                }

            } catch (e: Exception) {
                loginResponse.value = NetworkResult.Error("Usuario no encontrado")
            }
        } else {
            loginResponse.value = NetworkResult.Error("Sin conexión a internet")
        }
    }

    private suspend fun createNewUserSaveCall(newUser: NewUser) {
        loginResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.createUer(newUser)
                loginResponse.value = handleLoginResponse(response)
                val userInfo = loginResponse.value!!.data
                if (userInfo != null) {
                    Log.d("savingData", userInfo.toString())
                    dataStoreRepository.saveUID(userInfo.uid)
                    dataStoreRepository.saveJWT(userInfo.token)
                    Uuid.UUID_SAVE = userInfo.token
                }
            } catch (e: Exception) {
                loginResponse.value = NetworkResult.Error("Un usuario ya existe con esos datos")
            }
        } else {
            loginResponse.value = NetworkResult.Error("Sin conexion a internet")
        }
    }
    private fun handleLoginResponse(response: Response<UserResponse>): NetworkResult<UserResponse> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key limited.")
            }
            response.body()!!.token.isNullOrEmpty()-> {
                return NetworkResult.Error(response.message())
            }
            response.isSuccessful -> {
                val userData = response.body()
                Uuid.UUID_SAVE = response!!.body()!!.uid
                return NetworkResult.Success(userData!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
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

    private fun handleUserInfoResponse(response: Response<WrapperUser>): NetworkResult<WrapperUser> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key limited.")
            }
            response.body()!!.user.name.isNullOrEmpty() -> {
                return NetworkResult.Error(response.message())
            }
            response.isSuccessful -> {
                val userinfo = response.body()
                Log.d("PUTAMADRE", userInfo.toString())
                return NetworkResult.Success(userinfo!!)
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

    // public methods

    fun getUserInfomationForfragment() {

        viewModelScope.launch {
            readUid.collect{ value ->
                temp.value = value
            }
        }
    }

    fun getJWT() {

        viewModelScope.launch {
            readJWT.collect{ value ->
                jwtUser.value = value
            }
        }
    }
    fun login(phone: String, password: String) {
        val user = LoginPostRequest(phone, password)
        getLogin(user)
        //getUserInfo(phone)

        Log.d("UserData", loginResponse.value!!.data.toString())

        /*viewModelScope.launch {
            dataStoreRepository.saveUser(true)
        }*/
    }

    fun logout() {

        viewModelScope.launch {

            dataStoreRepository.saveUser(LOG_OUT)
            dataStoreRepository.saveCategory(DEFAULT_CATEGORY, 0)
            repository.local.deleteDatabase()
        }
    }

}