package de.finnbusse.snake

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity(), OnScoreChangeListener {

    private lateinit var gameView: GameView
    private lateinit var scoreTextView: TextView
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameView = findViewById(R.id.gameView)
        scoreTextView = findViewById(R.id.scoreTextView)

        // Setze den Listener für Score-Updates
        gameView.setOnScoreChangeListener(this)

        // Initialisiere GestureDetector
        gestureDetector = GestureDetector(this, SwipeGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean { // Ändert MotionEvent? zu MotionEvent
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
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

    inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onDown(e: MotionEvent): Boolean { // Ändert MotionEvent? zu MotionEvent
            return true
        }

        override fun onFling(
            e1: MotionEvent, // Ändert MotionEvent? zu MotionEvent
            e2: MotionEvent, // Ändert MotionEvent? zu MotionEvent
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffX = e2.x - e1.x
            val diffY = e2.y - e1.y

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        gameView.setDirection(Direction.RIGHT)
                    } else {
                        gameView.setDirection(Direction.LEFT)
                    }
                    return true
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        gameView.setDirection(Direction.DOWN)
                    } else {
                        gameView.setDirection(Direction.UP)
                    }
                    return true
                }
            }
            return false
        }
    }
}
