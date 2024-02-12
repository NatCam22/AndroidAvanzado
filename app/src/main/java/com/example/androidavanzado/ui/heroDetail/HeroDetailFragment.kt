package com.example.androidavanzado.ui.heroDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.androidavanzado.R
import android.R.drawable as drawable
import com.example.androidavanzado.databinding.FragmentHeroDetailBinding
import com.example.androidavanzado.domain.HeroDetail
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HeroDetailFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentHeroDetailBinding
    private val viewModel: HeroDetailViewModel by viewModels()
    private lateinit var mMap: GoogleMap
    private lateinit var hero: HeroDetail
    private val args: HeroDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHeroDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
        viewModel.getHero(args.heroName, args.token)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val mid = LatLng(0.0, 0.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mid))

    }

    private fun setListeners(){
        with(binding){
            like.setOnClickListener {
                viewModel.likeHero(hero.name, args.token)
            }
        }
    }
    private fun setObservers(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect{ state ->
                when(state) {
                    is HeroDetailViewModel.StateDetail.Idle -> {}
                    is HeroDetailViewModel.StateDetail.Error -> {}
                    is HeroDetailViewModel.StateDetail.Loading -> setLoading(state.b)
                    is HeroDetailViewModel.StateDetail.SuccessGetHero -> successGetHero(state.hero)
                    is HeroDetailViewModel.StateDetail.SuccessLikeHero -> successLikeHero(state.fav)
                }
            }
        }
    }

    private fun setLoading(b: Boolean){
        binding.loading.visibility = if(b) View.VISIBLE else View.INVISIBLE
    }
    private fun successLikeHero(fav: Boolean) {
        if(fav){
            binding.like.setImageResource(drawable.btn_star_big_on)
        } else {
            binding.like.setImageResource(drawable.btn_star_big_off)
        }
    }

    private fun successGetHero(heroDetail: HeroDetail) {
        hero = heroDetail
        updateView()
    }

    private fun updateView(){
        binding.heroName.text = hero.name
        binding.description.text = hero.description
        successLikeHero(hero.favorite)
        hero.locations.forEach { heroLocation ->
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(heroLocation.latitud.toDouble(), heroLocation.longitud.toDouble()))
                    .title("Hero Was Here!")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bola_dragon)))

        }
    }
}