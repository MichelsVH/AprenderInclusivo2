package com.example.aprenderinclusivo2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aprenderinclusivo2.databinding.FragmentMenuInicialBinding

class menu_inicial : Fragment() {

    private lateinit var binding: FragmentMenuInicialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_inicial, container, false)
        //click listener
    binding.mainBtnExercicios.setOnClickListener{findNavController().navigate(R.id.action_menu_inicial_to_menu_exercicios) }
    binding.mainBtnJogos.setOnClickListener{ findNavController().navigate(R.id.action_menu_inicial_to_menu_jogos) }

        return binding.root
    }

}