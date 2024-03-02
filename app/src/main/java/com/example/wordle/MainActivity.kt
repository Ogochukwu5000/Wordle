package com.example.wordle

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.view.View
import android.widget.Toast
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideKeyboard()

        val correctWord = findViewById<TextView>(R.id.correctWord)

        var wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        val guess = findViewById<EditText>(R.id.et_simple)

        val button = findViewById<Button>(R.id.button)
        val hide =   findViewById<Button>(R.id.hideKey)
        val restart =   findViewById<Button>(R.id.restart)

        val guess1Et = findViewById<TextView>(R.id.guess1Et)
        val guess2Et = findViewById<TextView>(R.id.guess2Et)
        val guess3Et = findViewById<TextView>(R.id.guess3Et)

        val guess1Check = findViewById<TextView>(R.id.guess1CheckRes)
        val guess2Check = findViewById<TextView>(R.id.guess2CheckRes)
        val guess3Check = findViewById<TextView>(R.id.guess3CheckRes)

        // Counter to keep track of which guess is being displayed
        var guessCounter = 1

        // Disable soft keyboard for the EditText

        hide.setOnClickListener {
            hideKeyboard()
        }
        restart.setOnClickListener{
            gameActive=true
            guess.text.clear()
            guess1Et.text = ""
            guess2Et.text = ""
            guess3Et.text = ""
            guess1Check.text = ""
            guess2Check.text = ""
            guess3Check.text = ""

            correctWord.visibility = View.INVISIBLE
            wordToGuess = FourLetterWordList.getRandomFourLetterWord()

            guessCounter = 1
        }

        button.setOnClickListener {

            if (!gameActive) {
                // Game is not active, do nothing
                return@setOnClickListener
            }

            hideKeyboard()
            // Get the text from the EditText field
            val text = guess.text.toString()
            // Clear the EditText field
            guess.text.clear()

            // Set the text of the corresponding EditText field based on the guess counter
            when (guessCounter) {
                1 -> {
                    guess1Et.text = text
                    val result1 = checkGuess(guess1Et.text.toString(),wordToGuess)
                    guess1Check.text = result1
                }
                2 -> {
                    guess2Et.text = text
                    val result2 = checkGuess(guess2Et.text.toString(), wordToGuess)
                    guess2Check.text = result2
                }
                3 -> {
                    guess3Et.text = text
                    val result3 = checkGuess(guess3Et.text.toString(), wordToGuess)
                    guess3Check.text = result3

                    if (result3 == wordToGuess){
                        Toast.makeText(this, "You Won!!", Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(this, "3 chances gone, click Restart", Toast.LENGTH_SHORT).show()
                    }
                    correctWord.text= wordToGuess
                    correctWord.visibility = View.VISIBLE
                    gameActive = false
                }
//
//
            }
            // Increment the guess counter
            guessCounter += 1
        }
    }
    private fun checkGuess(guess: String, wordToGuess: String): String {
        var result = ""
        val guess = guess.uppercase()
        val wordToGuess= wordToGuess.uppercase()
        for (i in guess.indices) {
            if (guess.length == 4) {
                if (guess[i] == wordToGuess[i]) {
                    result += "O"
                } else if (guess[i] in wordToGuess) {
                    result += "+"
                } else {
                    result += "X"
                }
            }
            else{
//                guessCounter -= 1
                Toast.makeText(this, "Please enter a 4-letter word", Toast.LENGTH_SHORT).show()
            }
        }
        return result
    }
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0) }
    }
}
