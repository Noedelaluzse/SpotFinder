package com.example.spotfinder.util

class Constants {
    companion object {
        const val X_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI2NDM1ZjRmYTQyOWNjOTUzZTBmMjk3MzQiLCJuYW1lIjoiTm9lIGRlIGxhIGx1eiIsImlhdCI6MTY4MTM0NjAzMSwiZXhwIjoxNjgxMzUzMjMxfQ.aJzVTCzGOomnalXEc6T-IW9Ir06hmCQ1k4IGInt4Yb0"
        const val BASE_URL = "https://9c17-2806-2f0-8000-eb15-f536-f84c-776e-d9cb.ngrok-free.app/api/"
        const val BASE_POKEMON_URL = "https://pokeapi.co/api/v2/"

        // API Query Keys
        const val QUERY_CATEGORY = "category"

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

        const val BUNDLE_LKEY = "placeBundle"

        const val LOG_IN = "login"
        const val LOG_OUT = "logout"
        const val ERROR_CASE = "error"
    }
}