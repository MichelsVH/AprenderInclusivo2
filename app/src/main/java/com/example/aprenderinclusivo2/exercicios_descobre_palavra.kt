package com.example.aprenderinclusivo2

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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

class exercicios_descobre_palavra: Fragment() {

    private lateinit var binding: FragmentExerciciosDescobrePalavraBinding
    private lateinit var db: DBExercicios
    private var correctWord: String? = null
    private var firstSelectedTextView: TextView? = null
    private var secondSelectedTextView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercicios_descobre_palavra, container, false)
        db = DBExercicios.getDatabase(requireContext())

        BuscarInformacao()
        return binding.root
    }


    private fun criarCampos(palavra: String) {
        val layout = binding.layoutlinear1
        layout.removeAllViews()
        val shuffledLetters = palavra.toCharArray().toList().shuffled()
        firstSelectedTextView = null
        secondSelectedTextView = null
        for (i in shuffledLetters.indices) {
            val textView = TextView(requireContext()).apply {
                text = shuffledLetters[i].toString()
                textSize = 25f
                setTextColor(Color.BLACK)
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
                )
                tag = "char-$i"
            }
            layout.addView(textView)

            textView.setOnClickListener { view ->
                if (view.tag == "selected") {
                    // If the clicked TextView is already selected, attempt to swap it with the previously selected TextView
                    if (secondSelectedTextView != null) {
                        swapCharacters()
                    }
                } else {
                    // If the clicked TextView is not selected, mark it as selected
                    selectCharacter(view as TextView)
                    // Attempt to swap if both TextViews are selected
                    if (firstSelectedTextView != null && secondSelectedTextView != null) {
                        swapCharacters()
                        view.setBackgroundColor(Color.TRANSPARENT)
                    }
                }
                checkAnswer()
            }

        }

        correctWord = palavra
    }

    private fun selectCharacter(view: TextView) {
        if (firstSelectedTextView == null) {
            firstSelectedTextView = view
            view.setBackgroundColor(Color.LTGRAY)
            Log.d("SwapDebug", "First: ${view.text}")
        } else if (secondSelectedTextView == null) {
            secondSelectedTextView = view
            view.setBackgroundColor(Color.LTGRAY)
            Log.d("SwapDebug", "Second: ${view.text}")
        }
    }

    private fun swapCharacters() {
        if (firstSelectedTextView != null && secondSelectedTextView != null) {
            val tempText = firstSelectedTextView!!.text.toString()
            firstSelectedTextView!!.text = secondSelectedTextView!!.text
            secondSelectedTextView!!.text = tempText

            // Remove the background color from the selected TextViews
            firstSelectedTextView!!.setBackgroundColor(Color.TRANSPARENT)
            secondSelectedTextView!!.setBackgroundColor(Color.TRANSPARENT)
            
            // Reset the selected TextViews
            firstSelectedTextView = null
            secondSelectedTextView = null
        }
    }



    private fun checkAnswer() {
        correctWord?.let { word ->
            if (word.length == binding.layoutlinear1.childCount) {
                val isAnswerCorrect = (0 until word.length).all { i ->
                    val textView = binding.layoutlinear1.getChildAt(i) as TextView
                    textView.text.toString() == word[i].toString()
                }
                if (isAnswerCorrect) {
                    Log.d("Condition", "Resposta correta")
                    showCustomDialog()
                }
            }
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
        val imageResourceId = resources.getIdentifier(imagem, "drawable", requireContext().packageName)
        binding.imageView.setImageResource(imageResourceId)
    }

    fun showCustomDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_win, null)
        val dialogButton = dialogView.findViewById<Button>(R.id.dialogButton)


        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialogButton.setOnClickListener {
            // Reset the game state and UI.
            BuscarInformacao()
            // Dismiss the dialog
            dialog.dismiss()
        }

    }
}

