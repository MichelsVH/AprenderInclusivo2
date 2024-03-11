package com.example.aprenderinclusivo2.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ExerciciosDao {

    @Query("SELECT * FROM exercicios_animais WHERE id = :id")
    suspend fun animaisPorID(id: Int): Exercicios_Animais?
    @Query("SELECT * FROM exercicios_bandeiras WHERE id = :id")
    suspend fun bandeirasPorID(id: Int): Exercicios_Bandeiras?
    @Query("SELECT * FROM exercicios_cores WHERE id = :id")
    suspend fun coresPorID(id: Int): Exercicios_Cores?
    @Query("SELECT * FROM exercicios_vegetais WHERE id = :id")
    suspend fun vegetaisPorID(id: Int): Exercicios_Vegetais?

    @Query("SELECT * FROM exercicios_animais")
    suspend fun animaisTotal(): List<Exercicios_Animais>
    @Query("SELECT * FROM exercicios_bandeiras")
    suspend fun bandeirasTotal(): List<Exercicios_Bandeiras>
    @Query("SELECT * FROM exercicios_cores")
    suspend fun coresTotal(): List<Exercicios_Cores>
    @Query("SELECT * FROM exercicios_vegetais")
    suspend fun vegetaisTotal(): List<Exercicios_Vegetais>


}