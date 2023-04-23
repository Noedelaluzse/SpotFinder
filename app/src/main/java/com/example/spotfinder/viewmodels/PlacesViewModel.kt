package com.example.spotfinder.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.spotfinder.data.DataStoreRepository
import com.example.spotfinder.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.spotfinder.util.Constants.Companion.QUERY_CATEGORY
import com.example.spotfinder.util.MapCategoryToApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository) :
    AndroidViewModel(application) {

    private var categoryType = DEFAULT_CATEGORY

    var networkStatus = false
    var backOnline = false

    val readPlaceType = dataStoreRepository.readCategory
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveCategoryType(categoryType: String, categoryTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveCategory(categoryType, categoryTypeId)
    }

    fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

     fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

         viewModelScope.launch {
             readPlaceType.collect{ value ->
                 categoryType = value.selectedCategoryName
             }
         }

         val categoryTypeMapped = MapCategoryToApi(categoryType)


        queries[QUERY_CATEGORY] = categoryTypeMapped
        return queries
    }

    fun showNetworkStatus() {

        if (!networkStatus) {
            Toast.makeText(getApplication(), "Sin conexión a internet", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "Conexiòn reestablecida", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }
}