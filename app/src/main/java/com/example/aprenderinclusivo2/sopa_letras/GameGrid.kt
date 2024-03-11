package com.example.aprenderinclusivo2.sopa_letras

class GameGrid(private val size: Int) {
    private val grid: Array<Array<Char?>> = Array(size) { arrayOfNulls(size) }

    fun setWord(word: String, startX: Int, startY: Int, direction: Direction) {
        val length = word.length
        for (i in 0 until length) {
            val x = startX + i * direction.dx
            val y = startY + i * direction.dy
            if (x in 0 until size && y in 0 until size && grid[x][y] == null) {
                grid[x][y] = word[i]
            }
        }
    }

    fun getGrid(): Array<Array<Char?>> {
        return grid
    }

    fun findWord(word: String): Boolean {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (tryFindWord(word, i, j, Direction.HORIZONTAL) ||
                    tryFindWord(word, i, j, Direction.VERTICAL) ||
                    tryFindWord(word, i, j, Direction.DIAGONAL_UP) ||
                    tryFindWord(word, i, j, Direction.DIAGONAL_DOWN)
                ) {
                    return true
                }
            }
        }
        return false
    }

    private fun tryFindWord(word: String, startX: Int, startY: Int, direction: Direction): Boolean {
        val length = word.length
        for (i in 0 until length) {
            val x = startX + i * direction.dx
            val y = startY + i * direction.dy
            if (x !in 0 until size || y !in 0 until size || grid[x][y] != word[i]) {
                return false
            }
        }
        return true
    }

}


enum class Direction(val dx: Int, val dy: Int) {
    HORIZONTAL(1, 0),
    VERTICAL(0, 1),
    DIAGONAL_UP(1, -1),
    DIAGONAL_DOWN(-1, 1)
}
