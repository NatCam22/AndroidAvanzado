package com.example.androidavanzado.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidavanzado.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository): ViewModel(){

    private val _uiState = MutableStateFlow<State>(State.Idle())
    val uiState: StateFlow<State> = _uiState

    sealed class State{
        class Idle: State()
        class Error(val message: String): State()
        class Loading: State()
        class SuccessLogin(val token: String): State()
    }

    fun launchLogin(usuario: String, contraseña: String){
        viewModelScope.launch {
            val token = withContext(Dispatchers.IO){
                val creds = Credentials.basic(usuario, contraseña)
                repository.getToken(creds)
            }
            _uiState.value = State.SuccessLogin(token)
        }
    }
}