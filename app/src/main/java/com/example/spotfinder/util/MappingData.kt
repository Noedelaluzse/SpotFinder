package com.example.spotfinder.util



fun MapCategoryToApi(category: String ): String {

    return when(category) {
        "general" -> "all"
        "restaurante" -> "restaurant"
        "bar" -> "bar"
        "café" -> "cafe"
        "nightclub" -> "nightclub"
        "música en vivo" -> "live_music"
        "sport bar" -> "sports_bar"
        "parque" -> "park"
        else -> "all"
    }
}

fun MapCategoryToApp(category: String ): String {

    return when(category) {
        "all" -> "General"
        "restaurant" -> "Restaurante"
        "bar"  -> "Bar"
        "cafe" -> "Café"
        "nightclub"-> "Nightclub"
        "live_music"  -> "Música en vivo"
        "sports_bar"  -> "Sport bar"
        "park" -> "Parque"
        else -> "General"
    }
}