package com.example.spotfinder.util

class Constants {
    companion object {
        const val X_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI2NDM1ZjRmYTQyOWNjOTUzZTBmMjk3MzQiLCJuYW1lIjoiTm9lIGRlIGxhIGx1eiIsImlhdCI6MTY4MTM0NjAzMSwiZXhwIjoxNjgxMzUzMjMxfQ.aJzVTCzGOomnalXEc6T-IW9Ir06hmCQ1k4IGInt4Yb0"
        const val BASE_URL = "https://b548-2806-2f0-8000-63f0-516d-8ca8-8fb8-449e.ngrok-free.app/api/"
        const val BASE_POKEMON_URL = "https://pokeapi.co/api/v2/"

        // API Query Keys
        const val QUERY_CATEGORY = "category"

        // ROOM Database
        const val DATABASE_NAME = "places_database"
        const val PLACES_TABLE = "places_table"
        const val DEFAULT_CATEGORY = "all"


        const val PREFERENCES_NAME = "spot_finder"
        const val PREFERENCES_CATEGORY_NAME = "categoryName"
        const val PREFERENCES_CATEGORY_NAME_ID = "categoryNameId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"
    }
}