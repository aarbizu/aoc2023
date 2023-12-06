package org.aarbizu.aoc2023

import kotlin.math.pow

class DayFour {
    fun parseCards(input: List<String>): List<Card> {
        val cardRegex = """Card\s+(\d+):\s+([\d\s]+)\s+\|\s+([\d\s]+)""".toRegex()
        val spaces = """\s+""".toRegex()

        return input
            .map { cardRegex.find(it)!!.destructured }
            .map { destructured ->
                Card(
                    destructured.component1().toInt(),
                    destructured.component2().split(spaces),
                    destructured.component3().split(spaces)
                )
            }
    }

    fun findWinningTotal(input: List<String>): Int {
        return parseCards(input).sumOf {
            // what if there's duplicate numbers in either list?  may be safe to assume that isn't the case
            val matches = it.winCount()
            if (matches > 0) 2.0.pow(matches - 1.0) else 0.0
        }.toInt()
    }

    fun scoreCards(input: List<String>): Int {
        // need to use a multimap, so that additions of card copies can be accurately counted
        var initialStack = parseCards(input)
            .associateBy({ it.number },{ mutableListOf(it) })
            .toMutableMap()

        val wonCards = mutableListOf(*initialStack.values.flatten().toTypedArray())

        // do copies
        while (initialStack.isNotEmpty()) {
            val wonCardNumbers = initialStack
                .map { cards -> cards.value.filter { card -> card.winCount() > 0 }}
                .flatten()
                .map { IntRange(it.number + 1, it.number + it.winCount()) }
                .toList()
            val nextBatch = mutableListOf<Card>()

            wonCardNumbers.map { range ->
                range.forEach {
                    initialStack[it]?.let { cards ->
                        cards.first { card ->
                            val cardCopy = card.copy()
                            wonCards.add(cardCopy)
                            nextBatch.add(cardCopy)
                        }
                    }
                }
            }
            initialStack.clear()
            initialStack = nextBatch.groupByTo(mutableMapOf()) { it.number }
        }
        return wonCards.size
    }
}

data class Card(val number: Int, val winners: List<String>, val scratched: List<String>, var copy: Boolean = false) {
    fun winCount(): Int {
        return scratched.toSet().intersect(winners.toSet()).size
    }

    fun copy(): Card {
        return Card(this.number, this.winners, this.scratched, true)
    }
}