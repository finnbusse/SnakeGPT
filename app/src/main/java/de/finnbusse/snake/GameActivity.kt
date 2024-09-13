package de.finnbusse.snake

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity(), OnScoreChangeListener {

    private lateinit var gameView: GameView
    private lateinit var scoreTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameView = findViewById(R.id.gameView)
        scoreTextView = findViewById(R.id.scoreTextView)

        // Setze den Listener für Score-Updates
        gameView.setOnScoreChangeListener(this)

        // Steuerelemente für die Schlange
        findViewById<Button>(R.id.buttonUp).setOnClickListener {
            gameView.setDirection(Direction.UP)
        }
        findViewById<Button>(R.id.buttonDown).setOnClickListener {
            gameView.setDirection(Direction.DOWN)
        }
        findViewById<Button>(R.id.buttonLeft).setOnClickListener {
            gameView.setDirection(Direction.LEFT)
        }
        findViewById<Button>(R.id.buttonRight).setOnClickListener {
            gameView.setDirection(Direction.RIGHT)
        }
    }

    override fun onScoreChanged(score: Int) {
        runOnUiThread {
            scoreTextView.text = "Punkte: $score"
        }
    }

    override fun onGameOver() {
        runOnUiThread {
            val intent = Intent(this, GameOverActivity::class.java)
            intent.putExtra("score", gameView.getScore())
            startActivity(intent)
            finish()
        }
    }
}
