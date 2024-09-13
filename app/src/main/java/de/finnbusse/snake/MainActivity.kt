package de.finnbusse.snake

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager // Hinzugefügt



class MainActivity : AppCompatActivity() {

    private lateinit var highscoreTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        highscoreTextView = findViewById(R.id.textHighscore)
        updateHighscore()

        // Start-Button konfigurieren
        val startButton = findViewById<Button>(R.id.buttonStart)
        startButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        updateHighscore()
    }

    private fun updateHighscore() {
        val highscore = sharedPreferences.getInt("highscore", 0)
        highscoreTextView.text = "Highscore: $highscore"
    }
}
