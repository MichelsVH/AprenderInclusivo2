package com.example.aprenderinclusivo2

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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.aprenderinclusivo2.data.DBExercicios
import com.example.aprenderinclusivo2.data.Exercicios_Animais
import com.example.aprenderinclusivo2.data.Exercicios_Bandeiras
import com.example.aprenderinclusivo2.data.Exercicios_Cores
import com.example.aprenderinclusivo2.data.Exercicios_Vegetais
import com.example.aprenderinclusivo2.databinding.FragmentExerciciosDescobrePalavraBinding
import kotlinx.coroutines.launch

class exercicios_descobre_palavra : Fragment() {

    private lateinit var binding: FragmentExerciciosDescobrePalavraBinding
    private lateinit var db: DBExercicios
    private var shuffledLetters: List<Char>? = null
    private var correctWord: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercicios_descobre_palavra, container, false)
        db = DBExercicios.getDatabase(requireContext())

        BuscarInformacao()
        return binding.root
    }

    fun criarCampos(palavra: String) {
        val layout = binding.layoutlinear1
        layout.removeAllViews()
        val numeroDeEspacos = palavra.length
        for (i in 0 until numeroDeEspacos) {
            val editText = EditText(requireContext())
            editText.inputType = InputType.TYPE_CLASS_TEXT
            editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(1)) // Limits to one letter per EditText
            editText.gravity = Gravity.CENTER
            editText.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
            ) // Distributes space equally

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
                    // Additional logic can be added here if necessary
                }
            })

            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
            editText.setTextColor(Color.BLACK)
            layout.addView(editText)
        }
        // Correctly assigns the shuffled letters to the class-level variable
        val shuffledLetters: List<Char> = palavra.toCharArray().shuffled().toList()
        for (i in 0 until numeroDeEspacos) {
            val editText = layout.getChildAt(i) as EditText
            editText.setText(shuffledLetters[i].toString())
        }
        correctWord = palavra
    }


    private fun handleLetterClick(button: View) {
        // Assuming this method is meant for handling button clicks, which doesn't align with the current logic
        // This method should be adjusted based on the actual UI elements being used
    }

    private fun checkAnswer() {
        var isAnswerCorrect = true
        correctWord?.let { word ->
            for (i in 0 until word.length) {
                val editText = binding.layoutlinear1.getChildAt(i) as EditText
                if (editText.text.isEmpty() || editText.text.toString() != word[i].toString()) {
                    isAnswerCorrect = false
                    break
                }
            }
        }

        if (isAnswerCorrect) {
            // Handle correct answer (e.g., show success message, move to next exercise)
            // You can also add logic for offering rewards or increasing difficulty
        }
    }

    private fun BuscarInformacao() {
        lifecycleScope.launch {
            val exercicio = fetchRandomExercicio()
            exercicio?.let { displayExercicio(it) }
        }
    }

    private suspend fun fetchRandomExercicio(): Any? {
        val categorias = listOf("animais", "vegetais", "cores", "bandeiras")
        val categoriaSelecionada = categorias.random()
        return when (categoriaSelecionada) {
            "animais" -> db.ExerciciosDao().animaisTotal().random()
            "vegetais" -> db.ExerciciosDao().vegetaisTotal().random()
            "cores" -> db.ExerciciosDao().coresTotal().random()
            "bandeiras" -> db.ExerciciosDao().bandeirasTotal().random()
            else -> null
        }
    }

    private fun displayExercicio(exercicio: Any) {
        val (hint, numLetras, nome) = when (exercicio) {
            is Exercicios_Animais -> Triple("Dica: Animais", exercicio.numLetras, exercicio.nome)
            is Exercicios_Vegetais -> Triple("Dica: Vegetais/Frutas", exercicio.numLetras, exercicio.nome)
            is Exercicios_Cores -> Triple("Dica: Cores", exercicio.numLetras, exercicio.nome)
            is Exercicios_Bandeiras -> Triple("Dica: Bandeiras", exercicio.numLetras, exercicio.nome)
            else -> Triple("", 0, "")
        }

        val imagem = when (exercicio) {
            is Exercicios_Animais -> exercicio.imagem
            is Exercicios_Vegetais -> exercicio.imagem
            is Exercicios_Cores -> exercicio.imagem
            is Exercicios_Bandeiras -> exercicio.imagem
            else -> ""
        }
        binding.textView2.text = hint
        setImageResource(imagem)
        criarCampos(nome)
    }

    private fun setImageResource(imagem: String) {
        val imageView: ImageView = binding.imageView
        val imageResourceId = resources.getIdentifier(imagem, "drawable", requireContext().packageName)
        imageView.setImageResource(imageResourceId)
    }
}
