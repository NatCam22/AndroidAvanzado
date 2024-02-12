package com.example.androidavanzado.domain

data class HeroDetail(
    val name: String,
    var favorite: Boolean,
    val description: String,
    val locations: List<HeroLocationUI>
)