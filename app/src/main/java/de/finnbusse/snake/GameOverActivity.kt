package de.finnbusse.snake

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager // Hinzugefügt

class GameOverActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var currentScore: Int = 0
    private var highscore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        currentScore = intent.getIntExtra("score", 0)
        highscore = sharedPreferences.getInt("highscore", 0)

        // Highscore aktualisieren, falls nötig
        if (currentScore > highscore) {
            highscore = currentScore
            sharedPreferences.edit().putInt("highscore", highscore).apply()
        }

        val scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        scoreTextView.text = "Dein Score: $currentScore\nHighscore: $highscore"

        val buttonMainMenu = findViewById<Button>(R.id.buttonMainMenu)
        buttonMainMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Schließe alle vorherigen Aktivitäten
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
