package com.example.androidavanzado.data.remote
import android.net.Credentials
import com.example.androidavanzado.data.remote.request.HeroRequest
import com.example.androidavanzado.domain.HeroRemote
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface DragonBallAPI {
    @POST("api/heros/all")
    suspend fun getHeros(@Body heroRequest: HeroRequest, @Header("Authorization") token: String): List<HeroRemote>

    @POST("api/auth/login")
    suspend fun getToken(@Header("Authorization") credentials: String): String
}