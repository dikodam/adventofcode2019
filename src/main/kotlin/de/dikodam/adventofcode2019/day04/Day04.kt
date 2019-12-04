package de.dikodam.adventofcode2019.day04

import de.dikodam.adventofcode2019.utils.printTiming
import de.dikodam.adventofcode2019.utils.withTimer

fun main() {
    val (passwordRange, setupDuration) = withTimer {
        val (start, end) = day04input.split("-").map { it.toInt() }
        (start..end)
    }

    val (t1ValidPasswordCount, t1duration) = withTimer {
        passwordRange
            .filter { it.hasOnlyRisingDigits() }
            .filter { it.hasAdjacentDuplicateDigits() }
            .count()
    }

    val (t2ValidPasswordCount, t2duration) = withTimer {
        passwordRange
            .filter { it.hasOnlyRisingDigits() }
            .filter { it.hasExactly2DuplicateDigits() }
            .count()
    }

    println("Task 1: There are $t1ValidPasswordCount valid passwords in the range $passwordRange.")
    println("Task 2: There are $t2ValidPasswordCount valid passwords in the range $passwordRange.")
    printTiming(setupDuration, t1duration, t2duration)
}

fun Int.hasAdjacentDuplicateDigits(): Boolean {
    val digits = this.toString()
    return digits
        .dropLast(1)
        .zip(digits.drop(1))
        .any { (char, nextChar) -> char == nextChar }
}

fun Int.hasOnlyRisingDigits(): Boolean {
    val digits = this.toString()
    return digits
        .dropLast(1)
        .zip(digits.drop(1))
        .all { (char, nextChar) -> char <= nextChar }
}

fun Int.hasExactly2DuplicateDigits(): Boolean {
    val digits = this.toString()
    // case 1: 1123456 - exactly 2 matching at start
    if (digits[0] == digits[1] && digits[0] != digits[2]) {
        return true
    }
    // case 2. 123455 - exactly 2 matching at end
    if (digits[digits.lastIndex] == digits[digits.lastIndex - 1] && digits[digits.lastIndex] != digits[digits.lastIndex - 2]) {
        return true
    }

    // case 3. 123345 - exactly 2 matching somewhere in between
    for (index in 2 until digits.length) {
        if (digits[index - 2] != digits[index] && digits[index - 1] == digits[index] && digits[index + 1] != digits[index]) {
            return true
        }
    }
    return false
}

const val day04input = "372304-847060"


// testdata:
// 377799, true
// 444488, true
// 444489, false
// 112233, true
// 123444, false
// 111234, false


