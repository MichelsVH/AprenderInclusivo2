package com.example.aprenderinclusivo2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aprenderinclusivo2.databinding.FragmentMenuJogosBinding


class menu_jogos : Fragment() {

    private lateinit var binding: FragmentMenuJogosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_jogos, container, false)

    //Navegação
        //Palavras-cruzadas
        binding.jogosBtnJogo1.setOnClickListener{findNavController().navigate(R.id.action_menu_jogos_to_jogos_sopa_letras)}
        //Joga da Memória
        binding.jogosBtnJogo3.setOnClickListener{findNavController().navigate(R.id.action_menu_jogos_to_jogos_memoria)}
        //Jogo do Galo
        binding.jogosBtnJogo4.setOnClickListener{findNavController().navigate(R.id.action_menu_jogos_to_jogos_galo)}
        //Voltar ao menu inicial
        binding.btnReturn.setOnClickListener{findNavController().navigate(R.id.return_menu_inicial)}

        return binding.root
    }

}