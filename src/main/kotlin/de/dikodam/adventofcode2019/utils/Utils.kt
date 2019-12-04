package de.dikodam.adventofcode2019.utils


fun <T> withTimer(block: () -> T): Pair<T, Long> {
    val start = System.currentTimeMillis()
    val result = block()
    val end = System.currentTimeMillis()
    return Pair(result, end - start)
}

