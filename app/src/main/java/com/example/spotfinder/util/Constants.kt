package com.example.spotfinder.util

class Constants {
    companion object {
        const val X_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI2NDM1ZjRjNTQyOWNjOTUzZTBmMjk3MmYiLCJuYW1lIjoiR2lsYmVydG8gU2VzZW5hIiwiaWF0IjoxNjg0NzkxODcxLCJleHAiOjE2ODQ3OTkwNzF9.XLAYIZIIfshNnmkYLyP-1Fi54TRJOoKq6iQh7JIlOa4"
        const val BASE_URL = "https://ba63-189-174-77-144.ngrok-free.app/api/"
        const val BASE_POKEMON_URL = "https://pokeapi.co/api/v2/"

        // API Query Keys
        const val QUERY_CATEGORY = "category"
        const val QUERY_UID = "uidUser"

        // ROOM Database
        const val DATABASE_NAME = "places_database"
        const val PLACES_TABLE = "places_table"
        const val DATABASE_NAME2 = "user_database"
        const val USER_TABLE = "user_table"

        const val DEFAULT_CATEGORY = "all"


        const val PREFERENCES_NAME = "spot_finder"
        const val PREFERENCES_CATEGORY_NAME = "categoryName"
        const val PREFERENCES_CATEGORY_NAME_ID = "categoryNameId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"
        const val PREFERENCES_VALID_USER = "user"
        const val PREFERENCES_UID_USER = "uid_user"
        const val PREFERENCES_X_TOKEN = "x-token"

        const val BUNDLE_LKEY = "placeBundle"

        const val LOG_IN = "login"
        const val LOG_OUT = "logout"
        const val ERROR_CASE = "error"
        const val NONE_CASE = "none"
    }
}