package de.dikodam.adventofcode2019.days

import de.dikodam.adventofcode2019.utils.applyRepeatedly
import kotlin.math.abs

fun main() {
    val basePattern = listOf(0, 1, 0, -1)

    val signal = applyRepeatedly(100, day16input) { computePhase(it, basePattern) }

    println("Task 1: ${signal.take(8).joinToString(separator = "")}")
}

private fun computePhase(input: List<Int>, basePattern: List<Int>): List<Int> =
    (1..input.size)
        .map { lineNumber ->
            basePattern.generatePatternSequence(lineNumber)
                .zip(input.asSequence())
                .map { (v1, v2) -> v1 * v2 }
                .sum() % 10
        }
        .map { abs(it % 10) }


private fun List<Int>.generatePatternSequence(multiplier: Int): Sequence<Int> {
    val seq = this.asSequence()
        .flatMap { generateSequence { it }.take(multiplier) }
    return generateSequence { seq }.flatten().drop(1)
}


private val day16input =
    "59708072843556858145230522180223745694544745622336045476506437914986923372260274801316091345126141549522285839402701823884690004497674132615520871839943084040979940198142892825326110513041581064388583488930891380942485307732666485384523705852790683809812073738758055115293090635233887206040961042759996972844810891420692117353333665907710709020698487019805669782598004799421226356372885464480818196786256472944761036204897548977647880837284232444863230958576095091824226426501119748518640709592225529707891969295441026284304137606735506294604060549102824977720776272463738349154440565501914642111802044575388635071779775767726626682303495430936326809"
        .toList()
        .map(Character::getNumericValue)

private val testInput = "12345678".toList().map(Character::getNumericValue)
