package org.aarbizu.aoc2023

val INPUT_DIR = "src/main/resources"

// copypasta from oac2022
fun parseToCharGrid(input: List<String>): CharGrid {
    return CharGrid(
        input.map {
            it.trim().toList().toMutableList()
        },
    )
}

// copypasta from aoc2022
class CharGrid(override val contents: List<MutableList<Char>>) : Grid<Char>(contents)

// copypasta from aoc2022
data class GridSquare<T>(val row: Int, val col: Int, var value: T) {
    var shortestPath: List<GridSquare<T>> = emptyList()

    override fun toString(): String {
        return "[$row,$col]=$value"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GridSquare<*>

        if (row != other.row) return false
        if (col != other.col) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        result = 31 * result + (value?.hashCode() ?: 0)
        return result
    }
}

// copypasta from aoc 2022
abstract class Grid<T>(open val contents: List<MutableList<T>>) {
    val rows: Int
        get() = contents.size
    val cols: Int
        get() = contents[0].size

    fun outOfBounds(
        r: Int,
        c: Int,
    ): Boolean {
        return (r < 0 || r >= rows || c < 0 || c >= cols)
    }

    fun at(
        r: Int,
        c: Int,
    ): GridSquare<T>? {
        return if (outOfBounds(r, c)) {
            null
        } else {
            gridValue(r, c)
        }
    }

    fun set(
        r: Int,
        c: Int,
        value: T,
    ) {
        if (outOfBounds(r, c)) {
            return
        }
        contents[r][c] = value
    }

    open operator fun get(
        r: Int,
        c: Int,
    ): T? {
        return if (outOfBounds(r, c)) null else contents[r][c]
    }

    fun gridValue(
        row: Int,
        col: Int,
    ): GridSquare<T> {
        return GridSquare(row, col, contents[row][col])
    }

    fun getNonDiagonalNeighbors(
        r: Int,
        c: Int,
    ): List<GridSquare<T>?> {
        return listOf(above(r, c), below(r, c), left(r, c), right(r, c))
    }

    fun getNonDiagonalNeighbors(
        r: Int,
        c: Int,
        predicate: (T) -> Boolean,
    ): List<GridSquare<T>> {
        return getNonDiagonalNeighbors(r, c).filterNotNull().filterNot { predicate(it.value) }
    }

    fun getDiagonalNeighbors(
        r: Int,
        c: Int,
    ): List<GridSquare<T>?> {
        return listOf(aboveRight(r, c), aboveLeft(r, c), belowRight(r, c), belowLeft(r, c))
    }

    fun getAllNeighbors(
        r: Int,
        c: Int,
    ): List<GridSquare<T>?> {
        return getNonDiagonalNeighbors(r, c) + getDiagonalNeighbors(r, c)
    }

    fun aboveRight(
        row: Int,
        col: Int,
    ): GridSquare<T>? {
        return if (row <= 0 || col >= cols - 1) {
            null
        } else {
            gridValue(row - 1, col + 1)
        }
    }

    fun aboveLeft(
        row: Int,
        col: Int,
    ): GridSquare<T>? {
        return if (row <= 0 || col <= 0) {
            null
        } else {
            gridValue(row - 1, col - 1)
        }
    }

    fun belowRight(
        row: Int,
        col: Int,
    ): GridSquare<T>? {
        return if (row >= rows - 1 || col >= cols - 1) {
            null
        } else {
            gridValue(row + 1, col + 1)
        }
    }

    fun belowLeft(
        row: Int,
        col: Int,
    ): GridSquare<T>? {
        return if (row >= rows - 1 || col <= 0) {
            null
        } else {
            gridValue(row + 1, col - 1)
        }
    }

    fun above(
        row: Int,
        col: Int,
    ): GridSquare<T>? {
        return if (row <= 0) {
            null
        } else {
            gridValue(row - 1, col)
        }
    }

    fun below(
        row: Int,
        col: Int,
    ): GridSquare<T>? {
        return if (row >= rows - 1) {
            null
        } else {
            gridValue(row + 1, col)
        }
    }

    fun left(
        row: Int,
        col: Int,
    ): GridSquare<T>? {
        return if (col <= 0) {
            null
        } else {
            gridValue(row, col - 1)
        }
    }

    fun right(
        row: Int,
        col: Int,
    ): GridSquare<T>? {
        return if (col >= cols - 1) {
            null
        } else {
            gridValue(row, col + 1)
        }
    }

    fun getGridSet(): Set<GridSquare<T>> {
        return contents.flatMapIndexed { row: Int, colValues: MutableList<T> ->
            List(colValues.size) { col: Int -> at(row, col)!! }
        }.toSet()
    }

    fun perimeter(): Set<GridSquare<T>> {
        val gridSquareSet = mutableSetOf<GridSquare<T>>()

        (0 until cols).forEach { gridSquareSet.add(gridValue(0, it)) }
        (0 until cols).forEach { gridSquareSet.add(gridValue(contents.lastIndex, it)) }
        (0 until rows).forEach { gridSquareSet.add(gridValue(it, 0)) }
        (0 until rows).forEach { gridSquareSet.add(gridValue(it, cols - 1)) }

        return gridSquareSet
    }

    override fun toString(): String {
        return contents.joinToString("\n") { list -> list.joinToString("") }
    }

    fun search(clause: (T) -> Boolean): List<GridSquare<T>> {
        val matching = mutableListOf<GridSquare<T>>()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (clause.invoke(contents[i][j])) {
                    at(i, j)?.also { matching.add(it) }
                }
            }
        }
        return matching
    }
}
