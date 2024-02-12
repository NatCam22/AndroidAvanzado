package com.example.androidavanzado.ui.heroList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidavanzado.databinding.FragmentHeroListBinding
import com.example.androidavanzado.domain.Hero
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HeroListFragment: Fragment(){
    private lateinit var binding: FragmentHeroListBinding
    private val viewModel: HeroListViewModel by viewModels()
    private val args: HeroListFragmentArgs by navArgs()

    val heroAdapter = HeroAdapter{
        findNavController().navigate(HeroListFragmentDirections.actionHeroListFragmentToHeroDetailFragment(it.name, args.token))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHeroListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        configurarRecyclerView()
        viewModel.getHeroList(args.token)
    }
    private fun configurarRecyclerView(){
        binding.rvListHeros.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvListHeros.adapter = heroAdapter
    }

    private fun setObservers(){
        viewLifecycleOwner.lifecycleScope.launch {
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
    private fun successGetHeros(heroList: List<Hero>){
        heroAdapter.updateHeroList(heroList)
    }

}