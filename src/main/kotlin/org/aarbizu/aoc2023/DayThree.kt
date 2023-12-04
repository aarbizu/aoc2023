package org.aarbizu.aoc2023

data class GridNumber(val squares: List<GridSquare<Char>>, val symbolAdjacent: Boolean = false) {
    fun toInt(): Int {
        return squares.map {
            it.value
        }.fold("") { a, b -> a + b }
            .toInt()
    }

    override fun toString(): String {
        return squares.map {
            it.value
        }.fold("") { a, b -> a + b } + ":$symbolAdjacent"
    }
}

fun <T> GridSquare<T>.isAdjacentTo(
    grid: CharGrid,
    gridNumber: GridNumber,
): Boolean {
    return gridNumber.squares.any {
        grid.getAllNeighbors(it.row, it.col).any { neighbor ->
            neighbor == this
        }
    }
}

class DayThree {
    fun checkSchematic(input: List<String>): Int {
        val grid = parseToCharGrid(input)

        val numbersAdjacentToSymbols = getNumbersAdjacentToSymbols(grid)
        return numbersAdjacentToSymbols
            .filter {
                it.symbolAdjacent
            }
            .sumOf {
                it.toInt()
            }
    }

    fun checkForGears(input: List<String>): Int {
        val grid = parseToCharGrid(input)
        val numbers = mutableListOf<GridNumber>()
        val stars = mutableListOf<GridSquare<Char>>()

        getGridNumbersAndStars(grid, stars, numbers)

        return stars.map { star ->
            val filtered =
                numbers.filter { number ->
                    star.isAdjacentTo(grid, number)
                }
            if (filtered.size == 2) {
                filtered[0].toInt() * filtered[1].toInt()
            } else {
                0
            }
        }.sum()
    }

    fun getGridNumbersAndStars(
        grid: CharGrid,
        stars: MutableList<GridSquare<Char>>,
        numbers: MutableList<GridNumber>,
    ) {
        for (i in 0 until grid.rows) {
            var digits = mutableListOf<GridSquare<Char>>()
            var readingNumber = false
            for (j in 0 until grid.cols) {
                val location = grid.at(i, j)
                location?.let {
                    if (location.value == '*') {
                        stars.add(location)
                    }
                    if (readingNumber) {
                        if (location.value.isDigit()) {
                            digits.add(GridSquare(i, j, location.value))
                        } else {
                            numbers.add(GridNumber(digits))
                            digits = mutableListOf()
                            readingNumber = false
                        }
                    } else {
                        if (location.value.isDigit()) {
                            digits.add(GridSquare(i, j, location.value))
                            readingNumber = true
                        }
                    }
                    if (j + 1 == grid.cols && readingNumber) {
                        numbers.add(GridNumber(digits))
                        digits = mutableListOf()
                        readingNumber = false
                    }
                }
            }
        }
    }

    private fun getNumbersAdjacentToSymbols(grid: CharGrid): List<GridNumber> {
        val numbers = mutableListOf<GridNumber>()
        for (i in 0 until grid.rows) {
            var digits = mutableListOf<GridSquare<Char>>()
            var adjacentSym = false
            var readingNumber = false
            for (j in 0 until grid.cols) {
                val location = grid.at(i, j)
                if (readingNumber) {
                    if (location?.value?.isDigit() == true) {
                        digits.add(GridSquare(i, j, location.value))
                        adjacentSym = checkNeighbors(i, j, adjacentSym, grid)
                    } else {
                        numbers.add(GridNumber(digits, adjacentSym))
                        digits = mutableListOf()
                        readingNumber = false
                        adjacentSym = false
                    }
                } else {
                    if (location?.value?.isDigit() == true) {
                        readingNumber = true
                        digits.add(GridSquare(i, j, location.value))
                        adjacentSym = checkNeighbors(i, j, false, grid)
                    }
                }
                if (j + 1 == grid.cols && readingNumber) {
                    numbers.add(GridNumber(digits, adjacentSym))
                    digits = mutableListOf()
                    readingNumber = false
                    adjacentSym = false
                }
            }
        }
        return numbers
    }

    private fun checkNeighbors(
        i: Int,
        j: Int,
        adjacentSym: Boolean,
        grid: CharGrid,
    ): Boolean {
        var adjSym = adjacentSym
        adjSym = adjSym ||
            grid.getAllNeighbors(i, j)
                .filterNotNull()
                .filterNot {
                    it.value == '.'
                }
                .any {
                    !it.value.isWhitespace() && !it.value.isDigit()
                }
        return adjSym
    }
}
