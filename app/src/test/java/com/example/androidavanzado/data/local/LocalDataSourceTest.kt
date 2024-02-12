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

    @Test
    fun `WHEN getHero THEN success hero local`(){
        every { dao.getHero("Pepito") } returns HeroLocal("123", "Pepito Perez", "Es un señor distinguido", "-", true)
        val heroLocal = localDataSource.getHero("Pepito")
        Assert.assertEquals("Pepito Perez", heroLocal.name)
    }

    @Test
    fun `WHEN getFavorite THEN success boolean, true`(){
        every { dao.getFavorite("123") } returns true
        val fav = localDataSource.getFavorite("123")
        Assert.assertEquals(true, fav)
    }

    @After
    fun tearDown(){

    }
}