package com.example.aprenderinclusivo2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aprenderinclusivo2.databinding.FragmentMenuExerciciosBinding


class menu_exercicios : Fragment() {

    private lateinit var binding: FragmentMenuExerciciosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_exercicios, container, false)
//Navegação
        //Preencher Espaços
        binding.exerciciosBtnExercicio1.setOnClickListener{findNavController().navigate(R.id.action_menu_exercicios_to_exercicios_preencher_espacos)}
        //Descobre a Palavra
        binding.exerciciosBtnExercicio2.setOnClickListener{findNavController().navigate(R.id.action_menu_exercicios_to_exercicios_descobre_palavra)}
        //Corresponder
        binding.exerciciosBtnExercicio3.setOnClickListener{findNavController().navigate(R.id.action_menu_exercicios_to_exercicios_corresponder)}
        //Voltar ao menu inicial
        binding.btnReturn.setOnClickListener{findNavController().navigate(R.id.return_menu_inicial)}

        return binding.root
    }

}