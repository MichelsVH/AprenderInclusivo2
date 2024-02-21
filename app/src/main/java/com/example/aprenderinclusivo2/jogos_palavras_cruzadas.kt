package com.example.aprenderinclusivo2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aprenderinclusivo2.databinding.FragmentJogosPalavrasCruzadasBinding


class jogos_palavras_cruzadas : Fragment() {

    private lateinit var binding: FragmentJogosPalavrasCruzadasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jogos_palavras_cruzadas, container, false)
        return binding.root
    }

}