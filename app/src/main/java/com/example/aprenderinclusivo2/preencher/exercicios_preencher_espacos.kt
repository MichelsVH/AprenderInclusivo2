package com.example.aprenderinclusivo2.preencher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aprenderinclusivo2.R
import com.example.aprenderinclusivo2.databinding.FragmentExerciciosPreencherEspacosBinding


class exercicios_preencher_espacos : Fragment() {

    private lateinit var binding: FragmentExerciciosPreencherEspacosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_exercicios_preencher_espacos, container, false)

        binding.exerciciosBtnPreencherEspacosAnimais.setOnClickListener{
            val escolha = Bundle().apply{
                putString("escolha", "animais")
            }
            findNavController().navigate(R.id.action_exercicios_preencher_espacos_to_exercicios_preencher_espacos_animais,
                escolha)
        }

        binding.exerciciosBtnPreencherEspacosVegetais.setOnClickListener{
            val escolha = Bundle().apply{
                putString("escolha", "vegetais")
            }
            findNavController().navigate(R.id.action_exercicios_preencher_espacos_to_exercicios_preencher_espacos_animais,
                escolha)
        }

        binding.exerciciosBtnPreencherEspacosCores.setOnClickListener{
            val escolha = Bundle().apply{
                putString("escolha", "cores")
            }
            findNavController().navigate(R.id.action_exercicios_preencher_espacos_to_exercicios_preencher_espacos_animais,
                escolha)
        }

        binding.exerciciosBtnPreencherEspacosBandeiras.setOnClickListener{
            val escolha = Bundle().apply{
                putString("escolha", "bandeiras")
            }
            findNavController().navigate(R.id.action_exercicios_preencher_espacos_to_exercicios_preencher_espacos_animais,
                escolha)
        }




        return binding.root
    }

}