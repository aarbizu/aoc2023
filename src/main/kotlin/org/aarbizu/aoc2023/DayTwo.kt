package org.aarbizu.aoc2023

class DayTwo(private val redCount: Int, private val greenCount: Int, private val blueCount: Int) {
    fun sumPossibleGameIds(games: List<String>): Int {
        return games.map {
            checkGameRounds(it)
        }.filter {
            it.second
        }.sumOf {
            it.first
        }
    }

    fun sumGamePowers(games: List<String>): Long {
        return games.map {
            getMinimumCubes(it)
        }.sumOf {
            val redVal = if (it["red"]!! > 0) it["red"]!!.toLong() else 1L
            val greenVal = if (it["green"]!! > 0) it["green"]!!.toLong() else 1L
            val blueVal = if (it["blue"]!! > 0) it["blue"]!!.toLong() else 1L

            redVal * greenVal * blueVal
        }
    }

    private fun isPossible(r: Map<String, Int>): Boolean {
        val redOk = if (r["red"] == null) true else r["red"]!! <= redCount
        val greenOk = if (r["green"] == null) true else r["green"]!! <= greenCount
        val blueOk = if (r["blue"] == null) true else r["blue"]!! <= blueCount

        return redOk && greenOk && blueOk
    }

    private fun checkGameRounds(rounds: String): Pair<Int, Boolean> {
        val idAndResults = rounds.split(": ")
        val id = idAndResults[0].split("Game ")
        val cubes = idAndResults[1].split("; ")
        val cubeTotals =
            cubes.map {
                val results =
                    it.split(", ").associate { color ->
                        val colorCount = color.split(" ")
                        colorCount[1] to colorCount[0].toInt()
                    }
                isPossible(results)
            }
        return Pair(id[1].toInt(), cubeTotals.all { it })
    }

    private fun getMinimumCubes(rounds: String): Map<String, Int> {
        val idAndResults = rounds.split(": ")
        val cubes = idAndResults[1].split("; ")

        val minColors =
            mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0,
            )

        cubes.map {
            it.split(", ").map { color ->
                val colorCount = color.split(" ")
                if (minColors[colorCount[1]]!! < colorCount[0].toInt()) {
                    minColors[colorCount[1]] = colorCount[0].toInt()
                }
            }
        }

        return minColors
    }
}
