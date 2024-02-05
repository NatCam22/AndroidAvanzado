package com.example.androidavanzado.data.remote

import android.util.Log
import com.example.androidavanzado.data.remote.request.HeroRequest
import com.example.androidavanzado.domain.Hero
import com.example.androidavanzado.domain.HeroRemote
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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
}