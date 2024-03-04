package com.example.aprenderinclusivo2

import android.graphics.Color
import android.os.Bundle
import android.view.DragEvent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
        val shuffledLetters = palavra.toCharArray().toList().shuffled()

        // Create a mutable list to hold the TextViews
        val textViews = mutableListOf<TextView>()
        val originalPositions = mutableMapOf<TextView, Int>()

        for (i in 0 until numeroDeEspacos) {
            val textView = TextView(requireContext())
            textView.text = shuffledLetters[i].toString()
            textView.textSize = 25f
            textView.setTextColor(Color.BLACK)
            textView.gravity = Gravity.CENTER
            textView.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
            ) // Distributes space equally

            // Assign a unique tag to each TextView
            textView.tag = "char-$i"

            // Set up the drag event listener
            textView.setOnLongClickListener {
                val dragShadowBuilder = View.DragShadowBuilder(it)
                it.startDrag(null, dragShadowBuilder, null, 0)
                true
            }

            layout.addView(textView)
            textViews.add(textView) // Add the TextView to the list
            originalPositions[textView] = i // Track the original position
        }

        // Set up the drop event listener on the LinearLayout
        layout.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val draggedView = event.localState as TextView
                    val draggedPosition = originalPositions[draggedView] ?: return@setOnDragListener false
                    val dropX = event.x.toInt()
                    val dropY = event.y.toInt()

                    // Determine the closest TextView to the drop point
                    val closestTextView = textViews.minByOrNull { view ->
                        val distanceX = view.x - dropX
                        val distanceY = view.y - dropY
                        Math.sqrt(distanceX * distanceX + distanceY * distanceY.toDouble())
                    }

                    // Ensure we found a closest TextView
                    closestTextView?.let {
                        // Swap the characters
                        val tempText = draggedView.text.toString()
                        draggedView.text = it.text
                        it.text = tempText

                        // Update the original positions
                        val dropPosition = originalPositions[it] ?: return@setOnDragListener false
                        originalPositions[draggedView] = dropPosition
                        originalPositions[it] = draggedPosition
                        true
                    } ?: return@setOnDragListener false
                }
                else -> false
            }
        }

        correctWord = palavra
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
