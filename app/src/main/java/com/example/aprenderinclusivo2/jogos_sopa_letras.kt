package com.example.aprenderinclusivo2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aprenderinclusivo2.databinding.FragmentJogosSopaLetrasBinding

class GameGrid(private val size: Int) {
    private val grid: Array<Array<Char?>> = Array(size) { arrayOfNulls(size) }

    fun setWord(word: String, startX: Int, startY: Int, direction: Direction) {
        // Placeholder for word placement logic
    }

    fun getGrid(): Array<Array<Char?>> {
        return grid
    }
}

enum class Direction {
    HORIZONTAL, VERTICAL
}
class jogos_sopa_letras : Fragment() {

    private lateinit var binding: FragmentJogosSopaLetrasBinding
    private lateinit var gameGrid: GameGrid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jogos_sopa_letras, container, false)

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
        val gridLayout = binding.root.findViewById<GridLayout>(R.id.gridLayout)
        gridLayout.rowCount = grid.size
        gridLayout.columnCount = grid[0].size

        for (i in grid.indices) {
            for (j in grid[i].indices) {
                val cell = TextView(context)
                cell.text = grid[i][j]?.toString() ?: ""
                cell.layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    columnSpec = GridLayout.spec(j, 1f)
                    rowSpec = GridLayout.spec(i, 1f)
                }
                gridLayout.addView(cell)
            }
        }
    }
}
