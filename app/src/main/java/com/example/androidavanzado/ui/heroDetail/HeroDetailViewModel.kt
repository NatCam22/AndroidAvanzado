package com.example.androidavanzado.ui.heroDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidavanzado.data.Repository
import com.example.androidavanzado.domain.Hero
import com.example.androidavanzado.domain.HeroDetail
import com.example.androidavanzado.ui.heroList.HeroListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(val repository: Repository): ViewModel() {

    private val _uiState = MutableStateFlow<StateDetail>(StateDetail.Idle())
    val uiState: StateFlow<StateDetail> = _uiState

    sealed class StateDetail{
        class Idle: StateDetail()
        class Error(val message: String): StateDetail()
        class Loading(val b: Boolean): StateDetail()
        class SuccessGetHero(val hero: HeroDetail): StateDetail()

        class SuccessLikeHero(val fav: Boolean): StateDetail()
    }

    fun getHero(heroName:String, token: String){
        _uiState.value = StateDetail.Loading(true)
        viewModelScope.launch{
            val hero = withContext(Dispatchers.IO){
                repository.getHeroDetail(heroName, token)
            }
            _uiState.value = StateDetail.Loading(false)
            _uiState.value = StateDetail.SuccessGetHero(hero)
        }
    }

    fun likeHero(heroName: String, token: String){
        _uiState.value = StateDetail.Loading(true)
        viewModelScope.launch{
            val fav = withContext(Dispatchers.IO){repository.likeHero(heroName, token)}
            _uiState.value = StateDetail.Loading(false)
            _uiState.value = StateDetail.SuccessLikeHero(fav)
        }
    }
}