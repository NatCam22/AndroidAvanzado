package com.example.androidavanzado.domain

import com.squareup.moshi.Json

data class HeroLocationRemote (
    @Json(name = "id") val id: String,
    @Json(name = "latitud") val latitud: String,
    @Json(name = "longitud") val longitud: String,
    @Json(name = "dateShow") val dateShow: String
)