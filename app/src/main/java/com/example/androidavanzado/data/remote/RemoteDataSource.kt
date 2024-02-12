package com.example.androidavanzado.data.remote

import com.example.androidavanzado.data.remote.request.HeroLikeRequest
import com.example.androidavanzado.data.remote.request.HeroLocationsRequest
import com.example.androidavanzado.data.remote.request.HeroRequest
import com.example.androidavanzado.domain.HeroLocationRemote
import com.example.androidavanzado.domain.HeroRemote
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val api: DragonBallAPI) {
    suspend fun getHeroList(token: String): List<HeroRemote>{
        val bearer = "Bearer $token"
        return api.getHeros(HeroRequest(), bearer)
    }
    suspend fun getToken(credentials: String): String{
        val token = api.getToken(credentials)
        return token
    }

    suspend fun getLocationsHero(heroId: String, token: String): List<HeroLocationRemote>{
        val bearer = "Bearer $token"
        return api.getLocationsHero(HeroLocationsRequest(heroId), bearer)
    }

    suspend fun likeHero(heroId: String, token: String){
        val bearer = "Bearer $token"
        api.likeHero(HeroLikeRequest(heroId), bearer)
    }

    suspend fun getHero(heroName: String, token: String): HeroRemote{
        val bearer = "Bearer $token"
        return api.getHero(HeroRequest(heroName), bearer).first()
    }
}