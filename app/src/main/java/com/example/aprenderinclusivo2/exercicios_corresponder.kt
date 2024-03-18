package com.example.aprenderinclusivo2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aprenderinclusivo2.data.DBExercicios
import com.example.aprenderinclusivo2.databinding.FragmentExerciciosCorresponderBinding

class exercicios_corresponder : Fragment() {

    private lateinit var binding: FragmentExerciciosCorresponderBinding
    private lateinit var db: DBExercicios

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercicios_corresponder, container, false)
        db = DBExercicios.getDatabase(requireContext())

        return binding.root
    }



}
