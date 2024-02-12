package com.example.androidavanzado.data.local

import android.content.Context
import androidx.room.Room
import com.example.androidavanzado.domain.HeroLocal
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: HeroDAO): LocalDataSourceInterface{
    override fun getHeros(): List<HeroLocal>{
        return dao.getAll()
    }

    override fun insertHeros(heros: List<HeroLocal>){
        dao.insertAll(heros)
    }

    override fun setFavorite(fav: Boolean, hero_id: String) {
        dao.setFavorite(fav, hero_id)
    }

    override fun getHero(heroName: String): HeroLocal {
        return dao.getHero(heroName)
    }

    override fun getFavorite(heroId: String): Boolean {
        return dao.getFavorite(heroId)
    }
}