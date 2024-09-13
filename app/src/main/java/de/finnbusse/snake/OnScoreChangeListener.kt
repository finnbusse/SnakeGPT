package de.finnbusse.snake

interface OnScoreChangeListener {
    fun onScoreChanged(score: Int)
    fun onGameOver()
}
