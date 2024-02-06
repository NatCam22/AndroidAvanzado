package com.example.androidavanzado.utils

import com.example.androidavanzado.domain.Hero
import com.example.androidavanzado.domain.HeroLocal
import com.example.androidavanzado.domain.HeroRemote

fun generateUIHeros(size: Int) = (0 until size).map { Hero("name$it", "description$it", "photo$it",true) }

fun generateLocalHeros(size: Int) = (0 until size).map { HeroLocal("id$it", "name$it", "description$it", "photo$it", favorite = true) }

fun generateRemoteHeros(size: Int) = (0 until size).map { HeroRemote("id$it", "name$it", "description$it", "photo$it", favorite = false) }
