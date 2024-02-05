package com.example.androidavanzado.data.local

import android.content.Context
import androidx.room.Room
import com.example.androidavanzado.domain.HeroLocal
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: HeroDAO){
    fun getHeros(): List<HeroLocal>{
        return dao.getAll()
    }

    fun insertHeros(heros: List<HeroLocal>){
        dao.insertAll(heros)
    }
}