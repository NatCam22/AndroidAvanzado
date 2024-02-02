package com.example.androidavanzado.ui.heroList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidavanzado.databinding.ItemHeroBinding
import com.example.androidavanzado.domain.Hero

class HeroAdapter: RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {
    private var heroList: MutableList<Hero> = mutableListOf()
    class HeroViewHolder(private val binding: ItemHeroBinding): RecyclerView.ViewHolder(binding.root){
        fun mostrarHero(hero: Hero){
            binding.heroName.text = "${hero.name}"
            Glide
                .with(binding.root)
                .load(hero.photo)
                .into(binding.ivHeroPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(
            ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return heroList.count()
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.mostrarHero(heroList[position])
    }

    fun updateHeroList(heroList: List<Hero>){
        this.heroList = heroList.toMutableList()
        notifyDataSetChanged()
    }
}