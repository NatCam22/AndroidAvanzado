package com.example.androidavanzado.data.remote
import com.example.androidavanzado.data.remote.request.HeroLikeRequest
import com.example.androidavanzado.data.remote.request.HeroLocationsRequest
import com.example.androidavanzado.data.remote.request.HeroRequest
import com.example.androidavanzado.domain.HeroLocationRemote
import com.example.androidavanzado.domain.HeroRemote
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DragonBallAPI {
    @POST("api/heros/all")
    suspend fun getHeros(@Body heroRequest: HeroRequest, @Header("Authorization") token: String): List<HeroRemote>

    @POST("api/auth/login")
    suspend fun getToken(@Header("Authorization") credentials: String): String

    @POST("api/heros/all")
    suspend fun getHero(@Body heroRequest: HeroRequest, @Header("Authorization") token: String): List<HeroRemote>

    @POST("api/data/herolike")
    suspend fun likeHero(@Body heroLikeRequest: HeroLikeRequest, @Header("Authorization") token: String): Unit

    @POST("api/heros/locations")
    suspend fun getLocationsHero(@Body heroLocationRequest: HeroLocationsRequest, @Header("Authorization") token: String): List<HeroLocationRemote>

}