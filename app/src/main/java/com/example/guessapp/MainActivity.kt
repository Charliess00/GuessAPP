package com.example.guessapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text
import java.security.SecureRandom
import kotlin.random.Random
import kotlin.random.nextInt

@Suppress("CAST_NEVER_SUCCEEDS")
class MainActivity : AppCompatActivity() {

    private lateinit var btn: Button
    private lateinit var guessEdit: TextInputEditText
    private lateinit var attemptsEdit: TextView
    private lateinit var res: TextView
    private lateinit var error: TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inite()

        val random = SecureRandom()
        var secretNumber = random.nextInt(101)
        Log.d("Secret", secretNumber.toString())
        var guess: Int?
        var attempts = 0
        attemptsEdit.text = "0"

        btn.setOnClickListener{
            error.error = null
            guess = if (guessEdit.text.toString() == ""){
                null
            } else {
                guessEdit.text.toString().toInt()
            }
            if(guess in 0..100){
                if (guess != secretNumber) {
                    if (guess!! < secretNumber) {
                        res.text = "Загаданное число больше $guess."
                        guessEdit.text?.clear()
                    } else if (guess!! > secretNumber) {
                        res.text = "Загаданное число меньше $guess."
                        guessEdit.text?.clear()
                    }
                    attempts++
                    attemptsEdit.text = attempts.toString()
                    return@setOnClickListener
                }
                val toastTxt = "Вы угадали число $secretNumber \nза $attempts попыт(ки/ок/ку)."
                val mAlertDialog = AlertDialog.Builder(this@MainActivity)
                mAlertDialog.setIcon(android.R.drawable.btn_star_big_on)
                mAlertDialog.setTitle("Поздравляем!")
                mAlertDialog.setMessage(toastTxt)
                mAlertDialog.setPositiveButton("Сыграть еще!") { dialog, id ->
                    //perform some tasks here
                    guessEdit.text?.clear()
                    res.text = ""
                    error.error = null
                    secretNumber = random.nextInt(101)
                    Log.d("Secret", secretNumber.toString())
                    attempts = 0
                    attemptsEdit.text = "0"
                    onStop()
                    //Toast.makeText(this@MainActivity, "Yes", Toast.LENGTH_SHORT).show()
                }
                mAlertDialog.show()
            } else {
                error.error = "Введите число правильно!"
            }
        }
    }


    private fun inite() {
        btn = findViewById(R.id.btn)
        guessEdit = findViewById(R.id.guessEdit)
        attemptsEdit = findViewById(R.id.attempts)
        res = findViewById(R.id.res)
        error = findViewById(R.id.error)
    }
}