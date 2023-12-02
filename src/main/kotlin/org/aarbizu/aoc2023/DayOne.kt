package org.aarbizu.aoc2023

class DayOne {
    private val digits =
        arrayOf(
            "zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine",
        )
    private val digitMap =
        mapOf(
            "zero" to 0,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )

    fun computeCalibration(doc: List<String>): Long {
        return doc.map {
            it.filter { c -> c.isDigit() }
        }.map {
            "${it.first()}${it.last()}"
        }.sumOf { it.toLong() }
    }

    fun computeCalibration2(doc: List<String>): Long {
        return doc.map {
            val firstNumIdx = it.indexOfFirst { c -> c.isDigit() }
            val lastNumIdx = it.indexOfLast { c -> c.isDigit() }
            val firstDigitOrEmpty =
                if (firstNumIdx >= 0) {
                    firstWrittenNumberOrEmpty(it.substring(0, firstNumIdx))
                } else {
                    firstWrittenNumberOrEmpty(it)
                }

            val lastDigitOrEmpty =
                if (lastNumIdx >= 0) {
                    lastWrittenNumberOrEmpty(it.substring(lastNumIdx + 1, it.length))
                } else {
                    lastWrittenNumberOrEmpty(it)
                }

            if (firstDigitOrEmpty.isEmpty() && lastDigitOrEmpty.isEmpty()) {
                "${it[firstNumIdx]}${it[lastNumIdx]}"
            } else if (firstDigitOrEmpty.isEmpty() && lastDigitOrEmpty.isNotEmpty()) {
                "${it[firstNumIdx]}${digitMap[lastDigitOrEmpty]}"
            } else if (firstDigitOrEmpty.isNotEmpty() && lastDigitOrEmpty.isEmpty()) {
                "${digitMap[firstDigitOrEmpty]}${it[lastNumIdx]}"
            } else {
                "${digitMap[firstDigitOrEmpty]}${digitMap[lastDigitOrEmpty]}"
            }
        }.sumOf {
            it.toLong()
        }
    }

    fun firstWrittenNumberOrEmpty(s: String): String {
        return if (s.length < 3) {
            ""
        } else {
            var earliestMatch = ""
            var earliestIdx = s.lastIndex
            digits.forEach { digit ->
                val rex = digit.toRegex()
                s.indices.forEach {
                    if (rex.matchesAt(s, it) && it < earliestIdx) {
                        earliestMatch = digit
                        earliestIdx = it
                    }
                }
            }
            return earliestMatch
        }
    }

    fun lastWrittenNumberOrEmpty(s: String): String {
        return if (s.length < 3) {
            ""
        } else {
            var latestMatch = ""
            var latestIdx = 0
            digits.forEach { digit ->
                val rex = digit.toRegex()
                s.indices.reversed().forEach {
                    if (rex.matchesAt(s, it) && it >= latestIdx) {
                        latestMatch = digit
                        latestIdx = it
                    }
                }
            }
            return latestMatch
        }
    }
}
