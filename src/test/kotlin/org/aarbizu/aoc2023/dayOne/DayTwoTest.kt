package org.aarbizu.aoc2023.dayOne

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2023.DayTwo
import org.aarbizu.aoc2023.INPUT_DIR
import org.junit.jupiter.api.Test
import java.io.File

class DayTwoTest {
    private val sample =
        """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent()

    @Test
    fun totalSampleResults() {
        assertThat(DayTwo(12, 13, 14).sumPossibleGameIds(sample.lines())).isEqualTo(8)
    }

    @Test
    fun partOne() {
        println(DayTwo(12, 13, 14).sumPossibleGameIds(File("$INPUT_DIR/d2-p1.txt").readLines()))
    }

    @Test
    fun samplePowerNumber() {
        assertThat(DayTwo(12, 13, 14).sumGamePowers(sample.lines())).isEqualTo(2286)
    }

    @Test
    fun partTwo() {
        println(DayTwo(12, 13, 14).sumGamePowers(File("$INPUT_DIR/d2-p1.txt").readLines()))
    }
}
