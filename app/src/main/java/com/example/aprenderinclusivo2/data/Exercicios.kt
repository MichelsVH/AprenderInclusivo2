package com.example.aprenderinclusivo2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercicios_animais")
data class Exercicios(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val numLetras: Int,
    val imagem: String
)
@Entity(tableName = "exercicios_cores")
data class Exercicios_animais(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val numLetras: Int,
    val imagem: String
)
