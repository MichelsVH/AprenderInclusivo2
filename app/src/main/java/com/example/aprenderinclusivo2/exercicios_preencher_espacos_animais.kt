package com.example.aprenderinclusivo2

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aprenderinclusivo2.databinding.FragmentExerciciosPreencherEspacosAnimaisBinding


class exercicios_preencher_espacos_animais : Fragment() {

    private lateinit var binding: FragmentExerciciosPreencherEspacosAnimaisBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercicios_preencher_espacos_animais, container, false)

        //val id = 1

        val numeroDeEspacos = 7 // Substitua pelo número desejado
        val animalName = "aguia" // Substitua pelo nome do animal dinâmico
        criarCamposParaAnimal(animalName, numeroDeEspacos)

        binding.exerciciosBtnVerificar.setOnClickListener {
            verificarRespostas(animalName)
        }

        return binding.root
    }

    private fun criarCamposParaAnimal(animalName: String, numeroDeEspacos: Int) {
        val layout = binding.layoutlinear1 // Substitua pelo ID do seu layout

        for (i in 0 until numeroDeEspacos) {
            val editText = EditText(requireContext())
            editText.inputType = InputType.TYPE_CLASS_TEXT
            // Defina outros atributos conforme necessário
            editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(1)) // Limita a uma letra por EditText
            editText.gravity = Gravity.CENTER
            editText.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
            ) // Distribui igualmente o espaço



            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }

                override fun afterTextChanged(editable: Editable) {
                    // Aqui você pode adicionar lógica adicional se necessário
                }
            })

            editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
            layout.addView(editText)
        }
    }

    private fun verificarRespostas(animalName: String) {
        val layout = binding.layoutlinear1 // Substitua pelo ID do seu layout

        var respostaUsuario = ""
        for (i in 0 until layout.childCount) {
            val editText = layout.getChildAt(i) as EditText
            respostaUsuario += editText.text.toString()
        }

        if (respostaUsuario.equals(animalName, ignoreCase = true)) {
            // Resposta correta, forneça feedback positivo
            Toast.makeText(requireContext(), "Resposta correta!", Toast.LENGTH_SHORT).show()
        } else {
            // Resposta incorreta, forneça feedback negativo
            Toast.makeText(requireContext(), "Resposta incorreta!", Toast.LENGTH_SHORT).show()
        }
    }
}