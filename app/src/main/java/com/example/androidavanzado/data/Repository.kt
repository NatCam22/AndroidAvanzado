package com.example.androidavanzado.data

import android.content.Context
import com.example.androidavanzado.data.local.LocalDataSource
import com.example.androidavanzado.data.mappers.LocalToUIMapper
import com.example.androidavanzado.data.mappers.RemoteToLocalMapper
import com.example.androidavanzado.data.remote.DragonBallAPI
import com.example.androidavanzado.data.remote.RemoteDataSource
import com.example.androidavanzado.domain.Hero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val localToUIMapper: LocalToUIMapper,
    private val remoteToLocalMapper: RemoteToLocalMapper) {

    suspend fun getHeroList(token: String): List<Hero> {
        val localHeros = localDataSource.getHeros()
        if (localHeros.isEmpty()){
            val remoteHeros = remoteDataSource.getHeroList(token)
            //cast heroRemote to heroLocal
            val localHeros = remoteToLocalMapper.map(remoteHeros)
            localDataSource.insertHeros(localHeros)
            val herosAgain = localDataSource.getHeros()
            //cast heroLocal to hero
            return localToUIMapper.map(herosAgain)
        } else{
            //cast heroLocal to hero
            return localToUIMapper.map(localHeros)
        }

    }

    suspend fun getToken(credentials: String): String{
        return remoteDataSource.getToken(credentials)
    }
}