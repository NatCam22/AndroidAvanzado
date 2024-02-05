package com.example.androidavanzado.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.androidavanzado.domain.HeroLocal

@Dao
interface HeroDAO {
    @Query("SELECT * FROM heros")
    fun getAll(): List<HeroLocal>
    @Insert
    fun insertAll(heros: List<HeroLocal>)
}