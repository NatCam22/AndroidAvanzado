package com.example.androidavanzado.di

import android.content.Context
import androidx.room.Room
import com.example.androidavanzado.data.local.HeroDAO
import com.example.androidavanzado.data.local.HeroDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    fun providesHeroDataBase(@ApplicationContext context: Context): HeroDataBase{
        return Room.databaseBuilder(
            context,
            HeroDataBase::class.java,
            "hero-database"
        ).build()
    }
    @Provides
    fun providesHeroDAO(dataBase: HeroDataBase):HeroDAO{
        return dataBase.heroDao()
    }
}