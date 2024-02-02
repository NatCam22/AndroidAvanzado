package com.example.androidavanzado.data

import com.example.androidavanzado.data.remote.RemoteDataSource
import com.example.androidavanzado.domain.Hero

class Repository {
    private val remoteDataSource = RemoteDataSource()

    suspend fun getHeroList(token: String): List<Hero> {
        return remoteDataSource.getHeroList(token)
    }

    suspend fun getToken(credentials: String): String{
        return remoteDataSource.getToken(credentials)
    }
}