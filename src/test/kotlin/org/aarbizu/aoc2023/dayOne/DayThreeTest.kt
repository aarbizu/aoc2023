package org.aarbizu.aoc2023.dayOne

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2023.DayThree
import org.aarbizu.aoc2023.GridNumber
import org.aarbizu.aoc2023.GridSquare
import org.aarbizu.aoc2023.INPUT_DIR
import org.aarbizu.aoc2023.isAdjacentTo
import org.aarbizu.aoc2023.parseToCharGrid
import org.junit.jupiter.api.Test
import java.io.File

class DayThreeTest {
    private val sample =
        """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
        """.trimIndent()

    @Test
    fun getSchematicGrid() {
        val grid = parseToCharGrid(sample.lines())
        assertThat(grid.rows).isEqualTo(10)
        assertThat(grid.cols).isEqualTo(10)
    }

    @Test
    fun gridNumber() {
        val squares =
            listOf(
                GridSquare(0, 0, '1'),
                GridSquare(0, 0, '2'),
                GridSquare(0, 0, '3'),
                GridSquare(0, 0, '4'),
            )

        assertThat(GridNumber(squares, false).toInt()).isEqualTo(1234)
    }

    @Test
    fun partOne() {
//        assertThat(DayThree().checkSchematic(sample.lines())).isEqualTo(4361)
        println(DayThree().checkSchematic(File("$INPUT_DIR/d3-p1.txt").readLines()))
    }

    @Test
    fun checkAdjacency() {
        val grid = parseToCharGrid(sample.lines())
        val numbers = mutableListOf<GridNumber>()
        val stars = mutableListOf<GridSquare<Char>>()
        DayThree().getGridNumbersAndStars(grid, stars, numbers)

        val firstNumber = numbers.first()
        val firstStar = stars.first()

        assertThat(firstStar.isAdjacentTo(grid, firstNumber)).isEqualTo(true)

        val allAdjacent =
            numbers.filter {
                firstStar.isAdjacentTo(grid, it)
            }

        assertThat(allAdjacent.size).isEqualTo(2)
    }

    @Test
    fun partTwo() {
        println(DayThree().checkForGears(File("$INPUT_DIR/d3-p1.txt").readLines()))
    }
}
