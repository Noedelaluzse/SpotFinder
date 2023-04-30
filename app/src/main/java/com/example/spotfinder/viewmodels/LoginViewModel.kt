package com.example.spotfinder.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.spotfinder.data.DataStoreRepository
import com.example.spotfinder.util.Constants.Companion.LOG_IN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository) :
    AndroidViewModel(application)  {

    val readUser = dataStoreRepository.readValidUser.asLiveData()

    fun saveUser(option: String) { viewModelScope.launch {
        dataStoreRepository.saveUser(option)
        }
    }
}