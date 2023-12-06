package org.aarbizu.aoc2023.dayOne

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2023.DayFour
import org.aarbizu.aoc2023.INPUT_DIR
import org.junit.jupiter.api.Test
import java.io.File

class DayFourTest {
    private val sample = """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent()

    @Test
    fun sampleTotalWinnings() {
        val cards = DayFour().parseCards(sample.lines())
        assertThat(cards.size).isEqualTo(6)

        assertThat(DayFour().findWinningTotal(sample.lines())).isEqualTo(13)
    }

    @Test
    fun partOne() {
        println(DayFour().findWinningTotal(File("$INPUT_DIR/d4-p1.txt").readLines()))
    }

    @Test
    fun partTwoSample() {
        println(DayFour().scoreCards(sample.lines()))
    }

    @Test
    fun partTwo() {
        println(DayFour().scoreCards(File("$INPUT_DIR/d4-p1.txt").readLines()))
    }
}