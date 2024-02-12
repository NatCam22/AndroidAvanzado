package com.example.androidavanzado.data

import com.example.androidavanzado.data.local.LocalDataSource
import com.example.androidavanzado.data.mappers.LocalToUIMapper
import com.example.androidavanzado.data.mappers.RemoteToLocalMapper
import com.example.androidavanzado.data.mappers.RemoteToUILocationMapper
import com.example.androidavanzado.data.remote.RemoteDataSource
import com.example.androidavanzado.utils.generateLocalHeros
import com.example.androidavanzado.utils.generateRemoteHeros
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RepositoryWithMockTest {
    private lateinit var repository: Repository
    private val localDataSource = mockk<LocalDataSource>()
    private val remoteDataSource: RemoteDataSource = mockk()
    private val localToUIMapper: LocalToUIMapper = LocalToUIMapper()
    private val remoteToLocalMapper: RemoteToLocalMapper = RemoteToLocalMapper()
    private val remoteToUILocationMapper: RemoteToUILocationMapper = RemoteToUILocationMapper()

    @Before
    fun setUp(){
        repository = Repository(localDataSource, remoteDataSource, localToUIMapper, remoteToLocalMapper, remoteToUILocationMapper)
    }

    @Test
    fun `WHEN getHeroList and not empty db THEN success list`()= runTest{
        val expectedLocal = generateLocalHeros(3)
        every { localDataSource.getHeros() } returns expectedLocal

        val actual = repository.getHeroList("")
        Assert.assertTrue(actual.isNotEmpty())
    }

    @Test
    fun `WHEN getHeroList and empty db THEN success list`()= runTest{
        val localHeros = generateLocalHeros(3)
        val remoteHeros = generateRemoteHeros(3)

        every { localDataSource.getHeros() } returns  emptyList()
        every { localDataSource.insertHeros(remoteToLocalMapper.map(remoteHeros)) } just runs
        coEvery { remoteDataSource.getHeroList("") } returns remoteHeros

        val actual = repository.getHeroList("")

        verify(exactly = 2) { localDataSource.getHeros()}
        verify(exactly = 1) { localDataSource.insertHeros(remoteToLocalMapper.map(remoteHeros)) }
        coVerify(exactly = 1) { remoteDataSource.getHeroList("") }
    }

    @Test
    fun `WHEN getToken THEN success token`()= runTest{
        coEvery { remoteDataSource.getToken("") } returns "TOKEN"

        val actual = repository.getToken("")
        Assert.assertEquals("TOKEN", actual)
    }

}