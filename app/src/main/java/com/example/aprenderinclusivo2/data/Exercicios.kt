package com.example.aprenderinclusivo2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_exercicios")
data class Exercicios(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val numLetras: Int,
    val imagem: String
)
