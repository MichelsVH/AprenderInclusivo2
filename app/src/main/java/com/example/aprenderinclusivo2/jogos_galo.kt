package com.example.aprenderinclusivo2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aprenderinclusivo2.databinding.FragmentJogosGaloBinding


class jogos_galo : Fragment() {

    private lateinit var binding: FragmentJogosGaloBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jogos_galo, container, false)

        return binding.root
    }
}