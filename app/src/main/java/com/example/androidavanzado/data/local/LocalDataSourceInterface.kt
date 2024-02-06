package com.example.androidavanzado.data.local

import com.example.androidavanzado.domain.HeroLocal

interface LocalDataSourceInterface {
    fun getHeros(): List<HeroLocal>

    fun insertHeros(heros: List<HeroLocal>)
}