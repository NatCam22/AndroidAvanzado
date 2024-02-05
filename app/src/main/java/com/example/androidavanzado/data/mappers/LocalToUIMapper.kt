package com.example.androidavanzado.data.mappers

import com.example.androidavanzado.domain.Hero
import com.example.androidavanzado.domain.HeroLocal
import javax.inject.Inject

class LocalToUIMapper @Inject constructor() {
    fun map(localHeros: List<HeroLocal>): List<Hero>{
        return localHeros.map { Hero(it.name, it.description, it.photo, it.favorite) }
    }
}