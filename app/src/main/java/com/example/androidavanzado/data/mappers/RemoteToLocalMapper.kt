package com.example.androidavanzado.data.mappers

import com.example.androidavanzado.domain.Hero
import com.example.androidavanzado.domain.HeroLocal
import com.example.androidavanzado.domain.HeroRemote
import javax.inject.Inject

class RemoteToLocalMapper @Inject constructor(){
    fun map(remoteHeros: List<HeroRemote>): List<HeroLocal>{
        return remoteHeros.map{ HeroLocal(it.id, it.name, it.description, it.photo, it.favorite) }
    }
}