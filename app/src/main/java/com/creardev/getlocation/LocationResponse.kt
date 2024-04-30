package com.creardev.getlocation

data class LocationResponse(
    val data: LocationData
)

data class LocationData(
    val status: Int,
    val latitude: Double,
    val longitude: Double,
    val postal: String
)

