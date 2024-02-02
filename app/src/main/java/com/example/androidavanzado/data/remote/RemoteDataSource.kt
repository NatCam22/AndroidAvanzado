package com.example.androidavanzado.data.remote

import android.util.Log
import com.example.androidavanzado.data.remote.request.HeroRequest
import com.example.androidavanzado.domain.Hero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RemoteDataSource {
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://dragonball.keepcoding.education/")
        .build()
    private var api = retrofit.create(DragonBallAPI::class.java)
    suspend fun getHeroList(token: String): List<Hero>{
        val bearer = "Bearer $token"
        return api.getHeros(HeroRequest(), bearer)
    }
    suspend fun getToken(credentials: String): String{
        val token = api.getToken(credentials)
        println(token)
        return token
    }
}