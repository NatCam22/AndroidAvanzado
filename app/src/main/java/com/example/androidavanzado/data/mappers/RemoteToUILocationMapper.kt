package com.example.androidavanzado.data.mappers

import com.example.androidavanzado.domain.HeroLocal
import com.example.androidavanzado.domain.HeroLocationRemote
import com.example.androidavanzado.domain.HeroLocationUI
import javax.inject.Inject

class RemoteToUILocationMapper @Inject constructor() {
    fun map(remoteLocations: List<HeroLocationRemote>): List<HeroLocationUI>{
        return remoteLocations.map{ HeroLocationUI(it.latitud, it.longitud) }
    }
}