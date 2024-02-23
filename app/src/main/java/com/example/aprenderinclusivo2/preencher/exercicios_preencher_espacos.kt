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
    private var escolha: String? = null
    private fun setButtonClickListener(button: View, choice: String) {
        button.setOnClickListener {
            val bundle = Bundle().apply {
                putString("escolha", choice)
            }
            findNavController().navigate(R.id.action_exercicios_preencher_espacos_to_exercicios_preencher_espacos_informacao, bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_exercicios_preencher_espacos, container, false)

        setButtonClickListener(binding.exerciciosBtnPreencherEspacosAnimais, "animais")
        setButtonClickListener(binding.exerciciosBtnPreencherEspacosVegetais, "vegetais")
        setButtonClickListener(binding.exerciciosBtnPreencherEspacosCores, "cores")
        setButtonClickListener(binding.exerciciosBtnPreencherEspacosBandeiras, "bandeiras")

        return binding.root
    }
}