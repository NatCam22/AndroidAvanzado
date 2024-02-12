package com.example.androidavanzado.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidavanzado.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun loading(){
        showLoading(true)
    }

    private fun successLogin(token: String){
        showLoading(false)
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHeroListFragment(token))
    }

}