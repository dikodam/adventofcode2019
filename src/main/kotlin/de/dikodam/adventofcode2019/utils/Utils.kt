package de.dikodam.adventofcode2019.utils

import kotlin.math.abs

fun <T> withTimer(block: () -> T): Pair<T, Long> {
    val start = System.currentTimeMillis()
    val result = block()
    val end = System.currentTimeMillis()
    return Pair(result, end - start)
}

fun manhattanDistance(x: Int, y: Int) = abs(x) + abs(y)

// noob impl because I'm too lazy to think
fun lcm(a: Long, b: Long): Long {
    val (smaller, bigger) = if (a < b) a to b else b to a
    return generateSequence(bigger) { it + bigger }
        .filter { it % smaller == 0L }
        .first()
}

fun <T, R> compareEqualsBy(firstList: List<T>, secondList: List<T>, transform: (T) -> R) =
    firstList.map(transform) == secondList.map(transform)


data class TimingData(
    private val setupDuration: Long = -1,
    private val t1duration: Long = -1,
    private val t2duration: Long = -1
) {

    fun print() =
        println("Preparation time: ${setupDuration}ms. Task 1 duration: ${t1duration}ms. Task 2 duration: ${t2duration}ms.")
}


