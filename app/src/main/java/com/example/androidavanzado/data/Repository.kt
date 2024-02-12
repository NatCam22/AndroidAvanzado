package com.example.androidavanzado.data

import com.example.androidavanzado.data.local.LocalDataSourceInterface
import com.example.androidavanzado.data.mappers.LocalToUIMapper
import com.example.androidavanzado.data.mappers.RemoteToLocalMapper
import com.example.androidavanzado.data.mappers.RemoteToUILocationMapper
import com.example.androidavanzado.data.remote.RemoteDataSource
import com.example.androidavanzado.domain.Hero
import com.example.androidavanzado.domain.HeroDetail
import com.example.androidavanzado.domain.HeroLocationRemote
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSourceInterface,
    private val remoteDataSource: RemoteDataSource,
    private val localToUIMapper: LocalToUIMapper,
    private val remoteToLocalMapper: RemoteToLocalMapper,
    private val remoteToUILocationMapper: RemoteToUILocationMapper) {

    suspend fun getHeroList(token: String): List<Hero> {
        val localHeros = localDataSource.getHeros()
        if (localHeros.isEmpty()){
            val remoteHeros = remoteDataSource.getHeroList(token)
            //cast heroRemote to heroLocal
            val localHeros = remoteToLocalMapper.map(remoteHeros)
            localDataSource.insertHeros(localHeros)
            val herosAgain = localDataSource.getHeros()
            //cast heroLocal to hero
            return localToUIMapper.map(herosAgain)
        } else{
            //cast heroLocal to hero
            return localToUIMapper.map(localHeros)
        }

    }

    suspend fun getToken(credentials: String): String{
        return remoteDataSource.getToken(credentials)
    }

    suspend fun getHeroDetail(heroName: String, token: String): HeroDetail {
        //Reviso si el heroe existe en local, si no (por razones muy extrañas que no deberían suceder dada la construcción de la aplicación)
        //realiza la búsqueda a través de internet y retorna el heroe encontrado.
        //Pera la lista de posiciones siempre hace el llamado a red
        val localHeroWOLocations = localDataSource.getHero(heroName)
        localHeroWOLocations?.let {
            val locations = remoteDataSource.getLocationsHero(localHeroWOLocations.id, token)
            return HeroDetail(localHeroWOLocations.name, localHeroWOLocations.favorite, localHeroWOLocations.description, remoteToUILocationMapper.map(locations))
        }
        //En caso de que no se encuentre el heroe en local, buscamos remoto
        val remoteHeroWOLocations = remoteDataSource.getHero(heroName, token)
        val locations = remoteDataSource.getLocationsHero(remoteHeroWOLocations.id, token)
        return HeroDetail(remoteHeroWOLocations.name, remoteHeroWOLocations.favorite, remoteHeroWOLocations.description, remoteToUILocationMapper.map(locations))
    }

    suspend fun likeHero(name: String, token: String): Boolean{
        //Realizo ambas peticiones, tanto el cambio en el local como en el remoto
        //Primero necesitamos el id (para local y para favorito asumiendo que son distintos)
        val localHeroId = localDataSource.getHero(name).id
        val remoteHeroId = remoteDataSource.getHero(name, token).id
        //Luego para local se obtiene el estado actual del favorito del heroe cuyo nombre es :name y se cambia por su negación.
        localDataSource.setFavorite(!localDataSource.getFavorite(localHeroId), localHeroId)
        //Finalmente la petición de like hero cumple la misma función que el setFavorite con la negación del estado actual pero para remoto.
        remoteDataSource.likeHero(remoteHeroId, token)
        return localDataSource.getFavorite(localHeroId)
    }
}