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

    @Query("UPDATE heros SET favorite = :fav WHERE id = :heroId")
    fun setFavorite(fav: Boolean, heroId: String )

    @Query("SELECT * FROM heros WHERE name = :heroName LIMIT 1")
    fun getHero(heroName: String): HeroLocal

    @Query("SELECT favorite FROM heros WHERE id = :heroId")
    fun getFavorite(heroId: String): Boolean
}