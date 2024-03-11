package com.example.aprenderinclusivo2.sopa_letras

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aprenderinclusivo2.R
import com.example.aprenderinclusivo2.data.DBExercicios
import com.example.aprenderinclusivo2.data.ExerciciosDao
import com.example.aprenderinclusivo2.databinding.FragmentJogosSopaLetrasBinding

class jogos_sopa_letras : Fragment() {

    private lateinit var binding: FragmentJogosSopaLetrasBinding
    private lateinit var db: DBExercicios
    private lateinit var gameGrid: GameGrid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_jogos_sopa_letras, container, false)
        db = DBExercicios.getDatabase(requireContext())
        Log.d("Jogos", "Fragmento Sopa de Letras")

        // Initialize the game grid
        gameGrid = GameGrid(5)

        // Example: Place a word in the grid
        gameGrid.setWord("EXAMPLE", 0, 0, Direction.HORIZONTAL)

        // Display the grid
        displayGrid()
        return binding.root
    }

    private fun displayGrid() {
        val grid = gameGrid.getGrid()
        val gridLayout = binding.gridLayout
        gridLayout.rowCount = grid.size
        gridLayout.columnCount = grid[0].size

        for (i in grid.indices) {
            for (j in grid[i].indices) {
                val cell = TextView(context).apply {
                    text = grid[i][j]?.toString() ?: ""
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = GridLayout.LayoutParams.WRAP_CONTENT
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(j, 1f)
                        rowSpec = GridLayout.spec(i, 1f)
                    }
                }
                gridLayout.addView(cell)
            }
        }
    }

    private fun displayExercicio(exercicio: Any) {
        // Implementation for displaying exercises based on their type
    }

    /*private fun BuscarInformacao() {
        lifecycleScope.launch {
            val numberOfNames = gameGrid.size // Adjust as needed
            val names = fetchRandomNames(db.ExerciciosDao(), numberOfNames)
            names.forEachIndexed { index, name ->
                val direction = Direction.values().random() // Randomly select a direction
                val startX = index % gameGrid.size // Calculate starting X position
                val startY = index / gameGrid.size // Calculate starting Y position
                gameGrid.setWord(name, startX, startY, direction)
            }
            displayGrid() // Refresh the grid display
        }
    }*/

    private suspend fun fetchRandomNames(dao: ExerciciosDao, numberOfNames: Int): List<String> {
        val categorias = listOf("animais", "vegetais", "cores", "bandeiras")
        val exercicios = mutableListOf<String>()

        while (exercicios.size < numberOfNames) {
            val categoriaSelecionada = categorias.random()
            val exercicio = when (categoriaSelecionada) {
                "animais" -> dao.animaisTotal().random()?.nome
                "vegetais" -> dao.vegetaisTotal().random()?.nome
                "cores" -> dao.coresTotal().random()?.nome
                "bandeiras" -> dao.bandeirasTotal().random()?.nome
                else -> null
            }
            // Correctly access the 'nome' property of the exercicio object
            exercicio?.let { exercicios.add(it) }
        }

        return exercicios
    }

}
