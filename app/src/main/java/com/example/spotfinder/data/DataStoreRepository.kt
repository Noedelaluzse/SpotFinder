package com.example.spotfinder.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import com.example.spotfinder.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.spotfinder.util.Constants.Companion.LOG_OUT
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_CATEGORY_NAME
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_CATEGORY_NAME_ID
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_NAME
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_UID_USER
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_VALID_USER
import com.example.spotfinder.util.Constants.Companion.PREFERENCES_X_TOKEN
import com.example.spotfinder.util.Options
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okhttp3.internal.userAgent
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {

        val selectedCategory = stringPreferencesKey(PREFERENCES_CATEGORY_NAME)
        val selectedCategoryId = intPreferencesKey(PREFERENCES_CATEGORY_NAME_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
        val validUser = stringPreferencesKey(PREFERENCES_VALID_USER)
        val uidUser = stringPreferencesKey(PREFERENCES_UID_USER)
        val jwtToken = stringPreferencesKey(PREFERENCES_X_TOKEN)
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

    suspend fun saveUser(user: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.validUser] = user
        }
    }

    suspend fun saveUID(uid: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.uidUser] = uid
        }
    }
    suspend fun saveJWT(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.jwtToken] = token
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

    val readValidUser: Flow<String> = dataStore.data
        .catch { exception ->
            if ( exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val user = preferences[PreferencesKeys.validUser] ?: LOG_OUT
            user
        }

    val readUid: Flow<String> = dataStore.data
        .catch { exception ->
            if ( exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val uid = preferences[PreferencesKeys.uidUser] ?: ""
            uid.toString()
        }


    val readJWT: Flow<String> = dataStore.data
        .catch { exception ->
            if ( exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val jwt = preferences[PreferencesKeys.jwtToken] ?: ""
            jwt
        }

}

data class CategoryType(
    val selectedCategoryName: String,
    val selectedCategoryNameId: Int
)