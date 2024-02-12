package com.example.androidavanzado.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Hero(
    val name: String,
    val photo: String,
    val favorite: Boolean)