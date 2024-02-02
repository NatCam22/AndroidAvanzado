package com.example.androidavanzado.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androidavanzado.data.Repository
import com.example.androidavanzado.databinding.ActivityLoginBinding
import com.example.androidavanzado.ui.heroList.HeroListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity: AppCompatActivity() {
    private val repository = Repository()
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setObservers()
    }

    fun setListeners(){
        binding.loginButton.setOnClickListener {
            viewModel.launchLogin(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    fun setObservers(){
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.uiState.collect{ state ->
                when(state) {
                    is LoginViewModel.State.Idle -> idle()
                    is LoginViewModel.State.Error -> error(state.message)
                    is LoginViewModel.State.Loading -> loading()
                    is LoginViewModel.State.SuccessLogin -> successLogin(state.token)
                }
            }
        }
    }
    private fun showLoading(b: Boolean) {
        binding.loading.visibility = if(b) View.VISIBLE else View.INVISIBLE
    }
    private fun idle(){
        showLoading(false)
    }
    private fun error(message: String){
        showLoading(false)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun loading(){
        showLoading(true)
    }

    private fun successLogin(token: String){
        showLoading(false)
        HeroListActivity.lanzarActivity(this, token)
    }
}