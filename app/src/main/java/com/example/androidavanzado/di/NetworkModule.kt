package com.example.androidavanzado.di

import com.example.androidavanzado.data.remote.DragonBallAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesMoshi(): Moshi{
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
    @Provides
    fun providesRetrofit( moshi: Moshi): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://dragonball.keepcoding.education/")
            .build()
    }

    @Provides
    fun providesDragonBallApi(retrofit: Retrofit): DragonBallAPI{
        return retrofit.create(DragonBallAPI::class.java)
    }
}