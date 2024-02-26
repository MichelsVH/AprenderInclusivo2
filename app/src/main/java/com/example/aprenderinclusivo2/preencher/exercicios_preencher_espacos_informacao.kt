package com.example.aprenderinclusivo2.preencher

import android.graphics.Color
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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.aprenderinclusivo2.R
import com.example.aprenderinclusivo2.data.DBExercicios
import com.example.aprenderinclusivo2.data.Exercicios_Animais
import com.example.aprenderinclusivo2.data.Exercicios_Bandeiras
import com.example.aprenderinclusivo2.data.Exercicios_Cores
import com.example.aprenderinclusivo2.data.Exercicios_Vegetais
import com.example.aprenderinclusivo2.databinding.FragmentExerciciosPreencherEspacosInformacaoBinding
import kotlinx.coroutines.launch


class exercicios_preencher_espacos_informacao : Fragment() {

    private lateinit var binding: FragmentExerciciosPreencherEspacosInformacaoBinding
    private lateinit var db: DBExercicios
    private var id =  1
    private var escolha: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_exercicios_preencher_espacos_informacao, container, false)

        db = DBExercicios.getDatabase(requireContext())

        escolha = arguments?.getString("escolha")

        BuscarInformacao()

        return binding.root
    }

    private fun BuscarInformacao() {
        lifecycleScope.launch {

            Toast.makeText(requireContext(), escolha, Toast.LENGTH_SHORT).show()
            var exercicio: Any? = null

            when (escolha) {
                "animais" -> exercicio = db.ExerciciosDao().animaisPorID(id)
                "vegetais" -> exercicio = db.ExerciciosDao().vegetaisPorID(id)
                "cores" -> exercicio = db.ExerciciosDao().coresPorID(id)
                "bandeiras" -> exercicio = db.ExerciciosDao().bandeirasPorID(id)
            }
            exercicio?.let {
                val numeroDeEspacos = when (it) {
                    is Exercicios_Animais -> it.numLetras
                    is Exercicios_Bandeiras -> it.numLetras
                    is Exercicios_Cores -> it.numLetras
                    is Exercicios_Vegetais -> it.numLetras
                    else ->  0
                }
                val Nome = when (it) {
                    is Exercicios_Animais -> it.nome
                    is Exercicios_Bandeiras -> it.nome
                    is Exercicios_Cores -> it.nome
                    is Exercicios_Vegetais -> it.nome
                    else -> ""
                }
                val Imagem = when (it) {
                    is Exercicios_Animais -> it.imagem
                    is Exercicios_Bandeiras -> it.imagem
                    is Exercicios_Cores -> it.imagem
                    is Exercicios_Vegetais -> it.imagem
                    else -> ""
                }

                val imageView: ImageView = binding.imageView // Use binding to get the ImageView
                val imageResourceId = resources.getIdentifier(Imagem, "drawable", requireContext().packageName)
                imageView.setImageResource(imageResourceId)

                criarCampos(numeroDeEspacos)

                binding.exerciciosBtnVerificar.setOnClickListener {
                    verificarRespostas(Nome)
                }
            }
        }
    }
    fun criarCampos(numeroDeEspacos: Int) {
        val layout = binding.layoutlinear1
        layout.removeAllViews()
        for (i in 0 until numeroDeEspacos) {
            val editText = EditText(requireContext())
            editText.inputType = InputType.TYPE_CLASS_TEXT
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

            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
            editText.setTextColor(Color.BLACK)
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
            // Resposta correta, fornece feedback positivo
            Toast.makeText(requireContext(), "Resposta correta!", Toast.LENGTH_SHORT).show()
            id++
            BuscarInformacao()
        } else {
            // Resposta incorreta, fornece feedback negativo
            Toast.makeText(requireContext(), "Resposta incorreta!", Toast.LENGTH_SHORT).show()
        }
    }
}