package com.strawhead.ecolution.ui.tempmodel

import com.strawhead.ecolution.R

data class PlaceInfo(
    val image: Int,
    val placeName: String,
    val placeAddress: String,
    val distance: Double
)

val dummyPlaceInfo = listOf(
    PlaceInfo(R.drawable.photo_rumah, "FNAF Pizzeria", "Backroom street number 420", 6.9),
    PlaceInfo(R.drawable.photo_rumah, "FNAF Pizzeria", "Backroom street number 420", 6.9),
    PlaceInfo(R.drawable.photo_rumah, "FNAF Pizzeria", "Backroom street number 420", 6.9),
    PlaceInfo(R.drawable.photo_rumah, "FNAF Pizzeria", "Backroom street number 420", 6.9),
)
