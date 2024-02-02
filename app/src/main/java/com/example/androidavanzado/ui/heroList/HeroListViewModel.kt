package com.example.androidavanzado.ui.heroList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidavanzado.data.Repository
import com.example.androidavanzado.domain.Hero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class HeroListViewModel: ViewModel(){
    private val repository = Repository()
    private val _uiState = MutableStateFlow<State>(State.Idle())
    val uiState: StateFlow<State> = _uiState


    fun getHeroList(token: String){
        viewModelScope.launch{
            val heros = withContext(Dispatchers.IO){
                repository.getHeroList(token)
            }
            _uiState.value = State.SuccessGetHeros(heros)
        }
    }

    sealed class State{
        class Idle: State()
        class Error(val message: String): State()
        class Loading: State()
        class SuccessGetHeros(val heroList: List<Hero>): State()
        class HeroSelected(): State()
        class OnHerosUpdated(val heroList: List<Hero>) : State()
    }
}