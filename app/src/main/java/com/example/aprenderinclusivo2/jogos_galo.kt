package com.example.aprenderinclusivo2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aprenderinclusivo2.databinding.FragmentJogosGaloBinding


class jogos_galo : Fragment() {

    private lateinit var binding: FragmentJogosGaloBinding
    private lateinit var board: Array<Array<Char>>
    private var currentPlayer = 'X'
    private var pointsX = 0
    private var pointsO = 0
    private lateinit var buttons: List<Button>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jogos_galo, container, false)
        initBoard()
        buttons = listOf(
            binding.button, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )
        setupButtonListeners()
        updatePointsView()
        return binding.root
    }

    private fun initBoard() {
        board = Array(3) { Array(3) { ' ' } }
    }

    private fun updatePointsView() {
        val pointsTextView = binding.pointsTextView
        pointsTextView.text = "X=$pointsX O=$pointsO"
    }
    private fun setupButtonListeners() {

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                handleButtonClick(index, button)
            }
        }
    }

    private fun handleButtonClick(index: Int, button: Button) {
        val row = index /  3
        val col = index %  3
        if (board[row][col] == ' ') {
            board[row][col] = currentPlayer
            button.text = currentPlayer.toString()
            if (checkWin(board, currentPlayer)) {

                // Create an AlertDialog
                val builder = AlertDialog.Builder(requireContext())


                builder.setTitle("Game Over")
                builder.setMessage("Player $currentPlayer wins!")
                if(currentPlayer== 'X'){
                    currentPlayer='O'
                    pointsX++
                }else if(currentPlayer== 'O'){
                    currentPlayer='X'
                    pointsO++
                }
                updatePointsView()
                builder.setPositiveButton("OK") { dialog, which ->
                    // Reset the game state
                    initBoard()
                    // Reset the UI
                    buttons.forEach { it.text = "" }
                }
                builder.setCancelable(false)
                builder.show()
            } else if (checkDraw(board)) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Game Over")
                builder.setMessage("It's a draw!")
                builder.setPositiveButton("OK") { dialog, which ->
                    // Reset the game state
                    initBoard()
                    // Reset the UI
                    buttons.forEach { it.text = "" }
                }
                builder.setCancelable(false)
                builder.show()
            } else {
                currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            }
        }
    }

    private fun checkWin(board: Array<Array<Char>>, player: Char): Boolean {
        // Implement win check logic here
        for (i in board.indices) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true
        }

        return false
    }


    private fun checkDraw(board: Array<Array<Char>>): Boolean {
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] == ' ') {
                    return false // Found an empty cell, not a draw
                }
            }
        }
        return true // No empty cells found, it's a draw
    }

}
