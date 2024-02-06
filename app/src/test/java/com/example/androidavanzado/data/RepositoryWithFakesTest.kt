package com.example.androidavanzado.data

import com.example.androidavanzado.data.local.LocalDataSource
import com.example.androidavanzado.data.mappers.LocalToUIMapper
import com.example.androidavanzado.data.mappers.RemoteToLocalMapper
import com.example.androidavanzado.data.remote.RemoteDataSource
import com.example.androidavanzado.utils.fakes.FakeLocalDataSource
import com.example.androidavanzado.utils.generateLocalHeros
import com.example.androidavanzado.utils.generateRemoteHeros
import com.google.common.truth.Truth
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

class RepositoryWithFakesTest {
    private lateinit var repository: Repository
    private val localDataSource = FakeLocalDataSource()
    private val remoteDataSource: RemoteDataSource = mockk()
    private val localToUIMapper: LocalToUIMapper = LocalToUIMapper()
    private val remoteToLocalMapper: RemoteToLocalMapper = RemoteToLocalMapper()

    @Before
    fun setUp(){
        repository = Repository(localDataSource, remoteDataSource, localToUIMapper, remoteToLocalMapper)
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

        val remoteHeros = generateRemoteHeros(3)
        val localHeros = remoteToLocalMapper.map(remoteHeros)
        //Ya no tengo que darle respuesta a los metodos de localDataSource porque ya no es un mock sino una instancia de la clase fake.
        coEvery { remoteDataSource.getHeroList("") } returns remoteHeros

        val actual = repository.getHeroList("")

        coVerify(exactly = 1) { remoteDataSource.getHeroList("") }
        Truth.assertThat(actual).containsExactlyElementsIn(localToUIMapper.map(localHeros))
    }
}