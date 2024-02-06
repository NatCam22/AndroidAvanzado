package com.example.androidavanzado.utils.fakes

import com.example.androidavanzado.data.local.LocalDataSourceInterface
import com.example.androidavanzado.domain.HeroLocal

class FakeLocalDataSource: LocalDataSourceInterface {

    private val dataBase = mutableListOf<HeroLocal>()
    override fun getHeros(): List<HeroLocal>{
        return dataBase
    }

    override fun insertHeros(heros: List<HeroLocal>){
        dataBase.addAll(heros)
    }
}