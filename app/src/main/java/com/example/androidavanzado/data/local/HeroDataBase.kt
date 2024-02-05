package com.example.androidavanzado.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidavanzado.domain.Hero
import com.example.androidavanzado.domain.HeroLocal

@Database(entities = [HeroLocal::class], version = 1)
abstract class HeroDataBase: RoomDatabase() {
    abstract fun heroDao(): HeroDAO
}