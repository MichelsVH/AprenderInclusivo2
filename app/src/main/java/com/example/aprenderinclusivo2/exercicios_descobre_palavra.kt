package com.example.aprenderinclusivo2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aprenderinclusivo2.databinding.FragmentExerciciosDescobrePalavraBinding

class exercicios_descobre_palavra : Fragment() {

    private lateinit var binding: FragmentExerciciosDescobrePalavraBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercicios_descobre_palavra, container, false)
        return binding.root
    }
}