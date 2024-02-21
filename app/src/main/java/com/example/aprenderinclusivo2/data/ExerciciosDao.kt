package com.example.aprenderinclusivo2.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ExerciciosDao {

    @Upsert
    suspend fun upsertExercicios(exercicios: Exercicios)

    @Delete
    suspend fun apagarExercicios(exercicios: Exercicios)

    @Query("SELECT * FROM tabela_exercicios ORDER BY ID ASC")
    fun exerciciosOrdemCres(): LiveData<List<Exercicios>>

}