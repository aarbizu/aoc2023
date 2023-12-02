package org.aarbizu.aoc2023.dayOne

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2023.DayOne
import org.aarbizu.aoc2023.INPUT_DIR
import org.junit.jupiter.api.Test
import java.io.File

val sampleInput =
    """
    1abc2
    pqr3stu8vwx
    a1b2c3d4e5f
    treb7uchet
    """.trimIndent()

val sampleInput2 =
    """
    two1nine
    eightwothree
    abcone2threexyz
    xtwone3four
    4nineeightseven2
    zoneight234
    7pqrstsixteen
    """.trimIndent()

class DayOneTest {
    @Test
    fun getCalibrationValue_Sample() {
        println(DayOne().computeCalibration(sampleInput.lines()))
    }

    @Test
    fun getCalibrationValue_PartOne() {
        print(DayOne().computeCalibration(File("$INPUT_DIR/d1-p1.txt").readLines()))
    }

    @Test
    fun checkEarliestDigit() {
        assertThat(DayOne().firstWrittenNumberOrEmpty("two1nine")).isEqualTo("two")
        assertThat(DayOne().firstWrittenNumberOrEmpty("two")).isEqualTo("two")
        assertThat(DayOne().firstWrittenNumberOrEmpty("eightwothree")).isEqualTo("eight")
        assertThat(DayOne().firstWrittenNumberOrEmpty("abcone2threexyz")).isEqualTo("one")
        assertThat(DayOne().lastWrittenNumberOrEmpty("two1nine")).isEqualTo("nine")
        assertThat(DayOne().lastWrittenNumberOrEmpty("nine")).isEqualTo("nine")
        assertThat(DayOne().lastWrittenNumberOrEmpty("eightwothree")).isEqualTo("three")
        assertThat(DayOne().lastWrittenNumberOrEmpty("abcone2threexyz")).isEqualTo("three")
    }

    @Test
    fun getCalibrationValue_SampleTwo() {
        print(DayOne().computeCalibration2(sampleInput2.lines()))
    }

    @Test
    fun getCalibrationValue_PartTwo() {
        print(DayOne().computeCalibration2(File("$INPUT_DIR/d1-p1.txt").readLines()))
    }
}
