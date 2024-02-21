package com.example.aprenderinclusivo2.data

import androidx.lifecycle.LiveData

class RepositorioExercicios(private val exerciciosDao: ExerciciosDao) {

    val exerciciosOrdemCres: LiveData<List<Exercicios>> = exerciciosDao.exerciciosOrdemCres()

    suspend fun upsertExercicios(exercicios: Exercicios){
        exerciciosDao.upsertExercicios(exercicios)
    }


}