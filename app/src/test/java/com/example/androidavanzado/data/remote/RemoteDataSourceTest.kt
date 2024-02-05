package com.example.androidavanzado.data.remote

import com.example.androidavanzado.data.remote.request.HeroRequest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.create
import com.keepcoding.androidavanzado.utils.MockWebDispatcher
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteDataSourceTest{
    //SUT O UUT (Subject under testing/ Unit under testing)
    private lateinit var  remoteDataSource: RemoteDataSource
    private lateinit var api: DragonBallAPI
    //Dependencias

    @Before
    fun setUp(){
        val mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockWebDispatcher()
        mockWebServer.start()

        val okHttpClient = OkHttpClient.Builder().build()
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        api = Retrofit.Builder().client(okHttpClient).addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(mockWebServer.url("/")).build().create(DragonBallAPI::class.java)
    }

    @Test
    fun `WHEN getHeros THEN success list`() = runTest{
        //Given
        remoteDataSource = RemoteDataSource(api)
        //When
        val heroList = remoteDataSource.getHeroList("")
        //then
        Assert.assertEquals(true, heroList.isNotEmpty())
    }
}