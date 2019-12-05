package de.dikodam.adventofcode2019.day02

import de.dikodam.adventofcode2019.utils.printTiming
import de.dikodam.adventofcode2019.utils.withTimer


// OPcode 1 a b c: take values from index a and b, add them, store at index 'value of index c'
// OPcode 2 a b c: take values from index a and b, multiply them, store at index 'value of index c'
// OPcode 99     : terminate
// any other     : error

// start at index 0, execute command, increment index by 4

fun main() {
    val (memory, setupDuration) = withTimer {
        day02input
            .split(",")
            .map { it.toInt() }
            .toIntArray()
    }

    val (t1result, t1duration) = withTimer {
        runIntcodeProgram(memory.clone(), 12, 1)
    }
    println("Task 1: $t1result")

    val (t2result, t2duration) = withTimer {
        val (noun, verb) = (0..99)
            .flatMap { left -> (0..99).map { right -> Pair(left, right) } }
            .first { (noun, verb) -> runIntcodeProgram(memory.clone(), noun, verb) == 19690720 }

        100 * noun + verb
    }

    println("Task 2: $t2result")
    printTiming(setupDuration, t1duration, t2duration)
}

private fun runIntcodeProgram(memory: IntArray, noun: Int, verb: Int): Int {
    memory[1] = noun
    memory[2] = verb

    var instructionPointer = 0
    var terminated = false
    while (!terminated) {
        val instruction = memory[instructionPointer]
        val param1 = { memory[instructionPointer + 1] }
        val param2 = { memory[instructionPointer + 2] }
        val param3 = { memory[instructionPointer + 3] }
        when (instruction) {
            1 -> memory[param3()] = memory[param1()] + memory[param2()]
            2 -> memory[param3()] = memory[param1()] * memory[param2()]
            99 -> terminated = true
            else -> throw IllegalStateException("${memory[instructionPointer]} at $instructionPointer is an invalid opcode")
        }
        instructionPointer += 4
    }
    return memory[0]
}

private const val day02input =
    "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,1,19,9,23,1,23,6,27,2,27,13,31,1,10,31,35,1,10,35,39,2,39,6,43,1,43,5,47,2,10,47,51,1,5,51,55,1,55,13,59,1,59,9,63,2,9,63,67,1,6,67,71,1,71,13,75,1,75,10,79,1,5,79,83,1,10,83,87,1,5,87,91,1,91,9,95,2,13,95,99,1,5,99,103,2,103,9,107,1,5,107,111,2,111,9,115,1,115,6,119,2,13,119,123,1,123,5,127,1,127,9,131,1,131,10,135,1,13,135,139,2,9,139,143,1,5,143,147,1,13,147,151,1,151,2,155,1,10,155,0,99,2,14,0,0"
