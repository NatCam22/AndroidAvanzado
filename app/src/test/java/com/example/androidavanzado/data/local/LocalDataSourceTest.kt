package com.example.androidavanzado.data.local

import com.example.androidavanzado.domain.Hero
import com.example.androidavanzado.domain.HeroLocal
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocalDataSourceTest {
    //SUT
    private lateinit var localDataSource: LocalDataSource

    private lateinit var dao: HeroDAO

    @Before
    fun setUp(){
        dao = mockk()
        localDataSource = LocalDataSource(dao)
    }

    @Test
    fun `WHEN getHeros THEN success list`(){
        every { dao.getAll()} returns listOf(
            HeroLocal("Ernesto", "perez", "Tu amigo el frailejon", "-", true),
            HeroLocal("Mickey", "Mouse", "El raton que silba ", "-", true))
        //when
        val heroList = localDataSource.getHeros()
        //then
        Assert.assertTrue(heroList.isNotEmpty())
    }

    @After
    fun tearDown(){

    }
}