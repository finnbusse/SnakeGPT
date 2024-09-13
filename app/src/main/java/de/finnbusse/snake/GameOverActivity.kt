package de.finnbusse.snake

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val score = intent.getIntExtra("score", 0)

        val scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        scoreTextView.text = "Dein Score: $score"

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
