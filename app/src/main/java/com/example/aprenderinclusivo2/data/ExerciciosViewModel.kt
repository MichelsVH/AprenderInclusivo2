package com.example.aprenderinclusivo2.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ExerciciosViewModel(application: Application):AndroidViewModel(application) {

    private val exerciciosOrdemCres: LiveData<List<Exercicios>>
    private val repositorio: RepositorioExercicios

    init{
        val exerciciosDao = DBExercicios.getDatabase(application).ExerciciosDao()
        repositorio = RepositorioExercicios(exerciciosDao)
        exerciciosOrdemCres = repositorio.exerciciosOrdemCres
    }

    fun upsertExercicios(exercicios: Exercicios){
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.upsertExercicios(exercicios)
        }
    }
}