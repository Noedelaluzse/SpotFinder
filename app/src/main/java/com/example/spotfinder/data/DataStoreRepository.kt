package com.example.spotfinder.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import com.example.spotfinder.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_CATEGORY_NAME
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_CATEGORY_NAME_ID
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {

        val selectedCategory = stringPreferencesKey(PREFERENCES_CATEGORY_NAME)
        val selectedCategoryId = intPreferencesKey(PREFERENCES_CATEGORY_NAME_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveCategory(categoryType: String, categoryId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.selectedCategory] = categoryType
            preferences[PreferencesKeys.selectedCategoryId] = categoryId
        }
    }

    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.backOnline] = backOnline
        }
    }

    val readCategory: Flow<CategoryType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences->
            val selectedCategoryName = preferences[PreferencesKeys.selectedCategory] ?: DEFAULT_CATEGORY
            val selectedCategoryNameId = preferences[PreferencesKeys.selectedCategoryId] ?: 0
            CategoryType(
                selectedCategoryName,
                selectedCategoryNameId
            )
        }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if ( exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferencesKeys.backOnline] ?: false
            backOnline
        }
}

data class CategoryType(
    val selectedCategoryName: String,
    val selectedCategoryNameId: Int
)