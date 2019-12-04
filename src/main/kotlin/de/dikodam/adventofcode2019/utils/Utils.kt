package de.dikodam.adventofcode2019.utils
import kotlin.math.abs

fun <T> withTimer(block: () -> T): Pair<T, Long> {
    val start = System.currentTimeMillis()
    val result = block()
    val end = System.currentTimeMillis()
    return Pair(result, end - start)
}

fun manhattanDistance(x:Int, y:Int) = abs(x) + abs(y)

fun printTiming(
    setupDuration: Long = -1,
    t1duration: Long = -1,
    t2duration: Long = -1
) = println("Preparation time: ${setupDuration}ms. Task 1 duration: ${t1duration}ms. Task 2 duration: ${t2duration}ms.")


