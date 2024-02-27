package com.example.aprenderinclusivo2

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aprenderinclusivo2.databinding.FragmentJogosGaloBinding

// This class represents the Tic-Tac-Toe game fragment.
class jogos_galo : Fragment() {

    // Binding object to access the layout views directly.
    private lateinit var binding: FragmentJogosGaloBinding
    //  2D array to represent the game board.
    private lateinit var board: Array<Array<Char>>
    // Current player, initially 'X'.
    private var currentPlayer = 'X'
    // Points for each player.
    private var pointsX =  0
    private var pointsO =  0
    // Flag to indicate if the game is over.
    private var isGameOver = false
    // List of buttons representing the game board.
    private lateinit var buttons: List<Button>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jogos_galo, container, false)
        // Set up click listener for the return button to navigate back.
        binding.btnReturn.setOnClickListener { findNavController().navigate(R.id.action_jogos_galo_to_menu_jogos) }
        // Set up click listener for the restart button to reset the game.
        binding.btnRestart.setOnClickListener {
            initBoard()
            buttons.forEach { it.text = "" }
            pointsO =  0
            pointsX =  0
            updatePointsView()
        }
        // Initialize the game board.
        initBoard()
        // Initialize the list of buttons.
        buttons = listOf(
            binding.button, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )
        // Set up click listeners for the game buttons.
        setupButtonListeners()
        // Update the points view to reflect the initial state.
        updatePointsView()
        // Return the root view.
        return binding.root
    }

    // Initializes the game board to empty spaces.
    private fun initBoard() {
        board = Array(3) { Array(3) { ' ' } }
    }

    // Updates the points view to reflect the current scores.
    private fun updatePointsView() {
        val pointsTextView = binding.pointsTextView
        pointsTextView.text = "X=$pointsX   O=$pointsO"
    }

    // Sets up click listeners for the game buttons.
    private fun setupButtonListeners() {
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                handleButtonClick(index, button)
            }
        }
    }

    // Handles a button click on the game board.
    private fun handleButtonClick(index: Int, button: Button) {
        // If the game is over, ignore the click.
        if (isGameOver) return
        val row = index /  3
        val col = index %  3
        // If the clicked cell is empty, mark it with the current player's symbol.
        if (board[row][col] == ' ') {
            board[row][col] = currentPlayer
            button.text = currentPlayer.toString()
            // Check if the current player has won.
            if (checkWin(board, currentPlayer)) {
                // Update the winner's points.
                if (currentPlayer == 'X') {
                    pointsX++
                } else {
                    pointsO++
                }
                updatePointsView()
                showCustomDialog("Jogador $currentPlayer ganhou!")
            } else if (checkDraw(board)) {
                showCustomDialog("É um empate!")
            }
            else {
                // Switch the current player.
                currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            }
        }
    }

    // Checks if the current player has won the game.
    private fun checkWin(board: Array<Array<Char>>, player: Char): Boolean {
        // Check rows, columns, and diagonals for a winning condition.
        for (i in board.indices) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                isGameOver = true
                return true
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                isGameOver = true
                return true
            }
        }
        // Check diagonals.
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            isGameOver = true
            return true
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            isGameOver = true
            return true
        }
        return false
    }

    // Checks if the game is a draw.
    private fun checkDraw(board: Array<Array<Char>>): Boolean {
        // Check if all cells are filled (no empty spaces).
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] == ' ') {
                    return false // Found an empty cell, not a draw.
                }
            }
        }
        return true // No empty cells found, it's a draw.
    }
    fun showCustomDialog(message: String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_game_over, null)
        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialogMessage)
        val dialogButton = dialogView.findViewById<Button>(R.id.dialogButton)

        dialogMessage.text = message



        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialogButton.setOnClickListener {
            // Reset the game state and UI.
            initBoard()
            buttons.forEach { it.text = "" }
            isGameOver = false
            // Dismiss the dialog
            dialog.dismiss()
        }

    }

}