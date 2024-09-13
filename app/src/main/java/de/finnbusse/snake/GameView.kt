package de.finnbusse.snake

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val handler = Handler(Looper.getMainLooper())
    private val updateDelay = 300L  // Aktualisierung alle 300 Millisekunden
    private var snake = mutableListOf<Pair<Int, Int>>()
    private var direction = Direction.RIGHT
    private var apple = Pair(5, 5)
    private val numCells = 20
    private var cellSize = 0
    private var score = 0
    private var isGameOver = false

    private val paintSnake = Paint().apply { color = Color.GREEN }
    private val paintApple = Paint().apply { color = Color.RED }
    private val paintBackground = Paint().apply { color = Color.BLACK }

    private var scoreChangeListener: OnScoreChangeListener? = null

    fun setOnScoreChangeListener(listener: OnScoreChangeListener) {
        scoreChangeListener = listener
    }

    private val updateRunnable = object : Runnable {
        override fun run() {
            if (!isGameOver) {
                update()
                invalidate()
                handler.postDelayed(this, updateDelay)
            }
        }
    }

    init {
        resetGame()
        handler.post(updateRunnable)
    }

    private fun resetGame() {
        snake.clear()
        snake.add(Pair(numCells / 2, numCells / 2))
        direction = Direction.RIGHT
        generateApple()
        score = 0
        isGameOver = false
        scoreChangeListener?.onScoreChanged(score)
    }

    fun setDirection(newDirection: Direction) {
        // Verhindert das Umkehren der Richtung
        if ((direction == Direction.UP && newDirection != Direction.DOWN) ||
            (direction == Direction.DOWN && newDirection != Direction.UP) ||
            (direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
            (direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            direction = newDirection
        }
    }

    private fun update() {
        val head = snake.first()
        val newHead = when (direction) {
            Direction.UP -> Pair(head.first, head.second - 1)
            Direction.DOWN -> Pair(head.first, head.second + 1)
            Direction.LEFT -> Pair(head.first - 1, head.second)
            Direction.RIGHT -> Pair(head.first + 1, head.second)
        }

        // Kollision mit den Wänden
        if (newHead.first < 0 || newHead.first >= numCells ||
            newHead.second < 0 || newHead.second >= numCells) {
            isGameOver = true
            scoreChangeListener?.onGameOver()
            return
        }

        // Kollision mit sich selbst
        if (snake.contains(newHead)) {
            isGameOver = true
            scoreChangeListener?.onGameOver()
            return
        }

        snake.add(0, newHead)

        // Apfel essen
        if (newHead == apple) {
            score++
            scoreChangeListener?.onScoreChanged(score)
            generateApple()
        } else {
            snake.removeAt(snake.size - 1)
        }
    }

    private fun generateApple() {
        var newApple: Pair<Int, Int>
        do {
            newApple = Pair(Random.nextInt(numCells), Random.nextInt(numCells))
        } while (snake.contains(newApple))
        apple = newApple
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        cellSize = width / numCells

        // Hintergrund zeichnen
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        // Schlange zeichnen
        for (segment in snake) {
            val left = segment.first * cellSize.toFloat()
            val top = segment.second * cellSize.toFloat()
            val right = left + cellSize.toFloat()
            val bottom = top + cellSize.toFloat()
            canvas.drawRect(left, top, right, bottom, paintSnake)
        }

        // Apfel zeichnen
        val appleLeft = apple.first * cellSize.toFloat()
        val appleTop = apple.second * cellSize.toFloat()
        val appleRight = appleLeft + cellSize.toFloat()
        val appleBottom = appleTop + cellSize.toFloat()
        canvas.drawRect(appleLeft, appleTop, appleRight, appleBottom, paintApple)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler.removeCallbacks(updateRunnable)
    }
}
