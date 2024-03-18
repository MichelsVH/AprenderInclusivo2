package com.example.aprenderinclusivo2

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.aprenderinclusivo2.data.DBExercicios
import com.example.aprenderinclusivo2.databinding.FragmentJogosMemoriaBinding
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class jogos_memoria : Fragment() {

    private lateinit var binding: FragmentJogosMemoriaBinding
    private lateinit var cards: MutableList<MemoryCard>
    private lateinit var db: DBExercicios
    private val imageButtons = ArrayList<ImageButton>()
    private var firstFlippedCard: ImageButton? = null
    private var secondFlippedCard: ImageButton? = null
    private var isGameOver = false
    private lateinit var countDownTimer: CountDownTimer
    private var elapsedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, com.example.aprenderinclusivo2.R.layout.fragment_jogos_memoria, container, false)
        db = DBExercicios.getDatabase(requireContext())

        binding.btnReturn.setOnClickListener { findNavController().navigate(com.example.aprenderinclusivo2.R.id.action_jogos_memoria_to_menu_jogos) }
        // Manipulador de clique do botão de reiniciar
        binding.btnRestart.setOnClickListener {
            // Reset the game state and UI.
            isGameOver = false
            // Reset cards and UI elements
            cards.forEach { it.isFlipped = false } // Reset flipped state of all cards
            imageButtons.forEach {
                it.setForeground(ContextCompat.getDrawable(requireContext(), com.example.aprenderinclusivo2.R.mipmap.fundo_carta)) // Reset card images
                it.isEnabled = true
                it.isClickable = true
            }
            // Optionally, you can shuffle cards again if needed
            shuffleCards()
            assignImagesToButtons() // Reassign images to buttons
            countDownTimer.cancel()
            elapsedTime = 0
            startTimer()
        }

        getAllImageButtons(binding.ConstraintLayout11)
        logImageButtonIds()
        setupCards()
        return binding.root
    }

    private suspend fun fetchRandom(): List<String> {
        Log.d("memoria", "fetchRandom 1")
        val allImages = mutableListOf<String>()
        Log.d("memoria", "fetchRandom 2")
        // Fetch images from each category and add them to the allImages list
        allImages.addAll(db.ExerciciosDao().jogoMemoria())
        Log.d("memoria", "Selected images: $allImages")
        // Shuffle the images to ensure randomness
        allImages.shuffle()

        // Limit the total number of images to 8
        val limitedImages = allImages.take(8)

        // Log the selected image names

        return limitedImages
    }


    private fun getAllImageButtons(parent: ViewGroup) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child is ImageButton) {
                Log.d("memoria", "Encontrado")
                imageButtons.add(child)
            } else if (child is ViewGroup) {
                Log.d("memoria", "Encontrado Linear")
                getAllImageButtons(child)
            }
        }
        Log.d("memoria", "Total ImageButtons found: ${imageButtons.size}")
    }

    private fun logImageButtonIds() {
        for (imageButton in imageButtons) {
            Log.d("memoria", "Found ImageButton with ID: ${imageButton.id}")
        }
    }
    private fun setupCards() {
        lifecycleScope.launch {
            val imagePaths = fetchRandom()
            // Duplicate each image path to ensure each image is assigned to two buttons
            val duplicatedImagePaths = imagePaths.flatMap { listOf(it, it) }
            cards = duplicatedImagePaths.map { MemoryCard(it) }.toMutableList()
            shuffleCards()
            assignImagesToButtons()
            // Start the timer
            startTimer()
            elapsedTime = 0
        }
    }

    private fun shuffleCards() {
        cards.shuffle()
    }

    private fun assignImagesToButtons() {
        for ((index, imageButton) in imageButtons.withIndex()) {
            val imageResourceId = resources.getIdentifier(cards[index].imagem, "mipmap", requireContext().packageName)
            imageButton.setImageResource(imageResourceId)
            imageButton.tag = cards[index]
            imageButton.setOnClickListener {
                flipCard(imageButton)
            }
        }
    }

    private fun flipCard(imageButton: ImageButton) {
        val card = imageButton.tag as? MemoryCard
        if (card != null) {
            if (firstFlippedCard == null) {
                // This is the first card flipped
                firstFlippedCard = imageButton
                // Flip the card to show the image
                val imageResourceId = resources.getIdentifier(card.imagem, "mipmap", requireContext().packageName)
                val imageDrawable = ContextCompat.getDrawable(requireContext(), imageResourceId)
                imageButton.setForeground(imageDrawable)
                card.isFlipped = true
            } else if (secondFlippedCard == null && firstFlippedCard?.id != imageButton.id) {
                // This is the second card flipped and its ID is different from the first
                secondFlippedCard = imageButton
                // Flip the second card to show the image
                val imageResourceId = resources.getIdentifier(card.imagem, "mipmap", requireContext().packageName)
                val imageDrawable = ContextCompat.getDrawable(requireContext(), imageResourceId)
                secondFlippedCard?.setForeground(imageDrawable)
                card.isFlipped = true

                // Delay the comparison or disabling of buttons by 1 second
                Handler(Looper.getMainLooper()).postDelayed({
                    val firstCard = firstFlippedCard?.tag as? MemoryCard
                    if (firstCard != null && firstCard.imagem != card.imagem) {
                        // Cards are not the same, flip them back to default
                        val defaultDrawable = ContextCompat.getDrawable(requireContext(), com.example.aprenderinclusivo2.R.mipmap.fundo_carta)
                        firstFlippedCard?.setForeground(defaultDrawable)
                        secondFlippedCard?.setForeground(defaultDrawable)
                        // Reset the flipped state of both cards
                        firstCard.isFlipped = false
                        card.isFlipped = false
                    } else {
                        // Cards are the same, disable the buttons
                        firstFlippedCard?.isEnabled = false
                        firstFlippedCard?.isClickable = false
                        secondFlippedCard?.isEnabled = false
                        secondFlippedCard?.isClickable = false
                        // Optionally, change the appearance to indicate disabled state
                        // For example, using a custom drawable for the disabled state
                        // firstFlippedCard?.setBackgroundResource(R.drawable.disabled_button_background)
                        // secondFlippedCard?.setBackgroundResource(R.drawable.disabled_button_background)
                    }
                    // Reset firstFlippedCard and secondFlippedCard
                    firstFlippedCard = null
                    secondFlippedCard = null


                }, 500) // Delay of 1 second
                checkVictoryCondition()
            }
        }
    }
    private fun checkVictoryCondition() {
    // Check if all cards are flipped
    val allCardsFlipped = cards.all { it.isFlipped }
    if (allCardsFlipped) {
        // Victory condition met, all cards are flipped
        // Display a victory message or perform any other victory actions here
        Log.d("memoria", "Victory! All cards are flipped.")
        isGameOver = true
        countDownTimer.cancel()
        val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) - TimeUnit.MINUTES.toSeconds(minutes)
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        showCustomDialog("Tempo: $formattedTime ")
        // Optionally, disable the game buttons or navigate to a new screen
    }
}
    fun showCustomDialog(message: String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(com.example.aprenderinclusivo2.R.layout.dialog_win, null)
        val dialogMessage = dialogView.findViewById<TextView>(com.example.aprenderinclusivo2.R.id.dialogMessage)
        val dialogButton = dialogView.findViewById<Button>(com.example.aprenderinclusivo2.R.id.dialogButton)

        dialogMessage.text = message

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialogButton.setOnClickListener {
            // Reset the game state and UI.
            isGameOver = false
            // Reset cards and UI elements
            cards.forEach { it.isFlipped = false } // Reset flipped state of all cards
            imageButtons.forEach {
                it.setForeground(ContextCompat.getDrawable(requireContext(), com.example.aprenderinclusivo2.R.mipmap.fundo_carta)) // Reset card images
                it.isEnabled = true
                it.isClickable = true
            }
            // Optionally, you can shuffle cards again if needed
            shuffleCards()
            assignImagesToButtons() // Reassign images to buttons
            // Dismiss the dialog
            dialog.dismiss()

            elapsedTime = 0
            startTimer()
        }
    }
    private fun startTimer() {
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime += 1000 // Adiciona 1 segundo ao tempo decorrido

                val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) - TimeUnit.MINUTES.toSeconds(minutes)

                val formattedTime = String.format("%02d:%02d", minutes, seconds)

                binding.timer.text = "Tempo: $formattedTime"
            }

            override fun onFinish() {
                // Este método não será chamado, pois estamos usando Long.MAX_VALUE como o tempo inicial
            }
        }.start()
    }


}


data class MemoryCard(val imagem: String, var isFlipped: Boolean = false)
