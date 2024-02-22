package com.example.aprenderinclusivo2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercicios_animais")
data class Exercicios_Animais(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val numLetras: Int,
    val imagem: String
)
@Entity(tableName = "exercicios_cores")
data class Exercicios_Cores(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val numLetras: Int,
    val imagem: String
)
@Entity(tableName = "exercicios_bandeiras")
data class Exercicios_Bandeiras(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val numLetras: Int,
    val imagem: String
)
@Entity(tableName = "exercicios_vegetais")
data class Exercicios_Vegetais(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val numLetras: Int,
    val imagem: String
)
