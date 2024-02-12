package com.example.androidavanzado.utils.fakes

import com.example.androidavanzado.data.local.LocalDataSourceInterface
import com.example.androidavanzado.domain.HeroLocal

class FakeLocalDataSource: LocalDataSourceInterface {

    var dataBase = mutableListOf<HeroLocal>()
    override fun getHeros(): List<HeroLocal>{
        return dataBase
    }

    override fun insertHeros(heros: List<HeroLocal>){
        dataBase.addAll(heros)
    }

    override fun setFavorite(fav: Boolean, hero_id: String) {
        dataBase.first().favorite = fav
    }

    override fun getHero(heroName: String): HeroLocal {
        return dataBase.first()
    }

    override fun getFavorite(heroId: String): Boolean {
        return dataBase.first().favorite
    }
}