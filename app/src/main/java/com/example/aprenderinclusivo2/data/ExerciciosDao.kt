package com.example.aprenderinclusivo2.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ExerciciosDao {

    @Query("SELECT * FROM tabela_exercicios WHERE id = :id")
    suspend fun exerciciosPorID(id: Int): Exercicios?

}