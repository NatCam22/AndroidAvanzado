package com.example.androidavanzado.ui.heroList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidavanzado.databinding.ActivityHeroListBinding
import com.example.androidavanzado.domain.Hero
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HeroListActivity : AppCompatActivity() {
    companion object{
        val TOKEN = "TOKEN"
        fun lanzarActivity(context: Context, token:String){
            val intent = Intent(context, HeroListActivity::class.java)
            intent.putExtra(TOKEN, token)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityHeroListBinding
    private val viewModel: HeroListViewModel by viewModels()
    val heroAdapter = HeroAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = intent.getStringExtra(TOKEN)
        token?.let{
            Log.d("AQUI", token)
            viewModel.getHeroList(token)}

        mostrarListaHeros()
        setObservers()
        //setListeners()
    }
    private fun mostrarListaHeros(){
        binding.rvListHeros.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvListHeros.adapter = heroAdapter
    }

    private fun setObservers(){
        lifecycleScope.launch {
            viewModel.uiState.collect{ state ->
                when(state) {
                    is HeroListViewModel.State.HeroSelected -> {}
                    is HeroListViewModel.State.Idle -> {}
                    is HeroListViewModel.State.Error -> {}
                    is HeroListViewModel.State.Loading -> {}
                    is HeroListViewModel.State.SuccessGetHeros -> successGetHeros(state.heroList)
                    is HeroListViewModel.State.OnHerosUpdated -> successGetHeros(state.heroList)
                }
            }
        }
    }

    //private fun setListeners(){
    //}

    private fun successGetHeros(heroList: List<Hero>){
        heroAdapter.updateHeroList(heroList)
    }
}