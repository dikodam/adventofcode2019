package de.dikodam.adventofcode2019.days

import de.dikodam.adventofcode2019.utils.IntcodeComputer
import de.dikodam.adventofcode2019.utils.toIntCode
import java.util.concurrent.CompletableFuture

fun main() {
    val intCode = day07input.toIntCode()

    // Task 1
//    val maxThrustSignal1 = (0..4).toSet()
//        .permutate()
//        .map { phaseSetting -> runAmplifiers(intCode, phaseSetting) }
//        .max()!!

//    println("Task 1: Phase setting for maximal thrust signal is $maxThrustSignal1")

    // Task 2
    val maxThrustSignal2 =
//        (5..9).toSet()
//        .permutate()
        listOf(listOf(5, 6, 7, 8, 9))
            .map { phaseSetting -> runAmplifiers(intCode, phaseSetting) }
            .max()!!

    println("Task 2: Phase setting for maximal thrust signal is $maxThrustSignal2")

}

fun runAmplifiers(intCode: IntArray, phaseSequence: List<Int>): Int {
    val ampA = IntcodeComputer(intCode)
    val ampB = IntcodeComputer(intCode)
    val ampC = IntcodeComputer(intCode)
    val ampD = IntcodeComputer(intCode)
    val ampE = IntcodeComputer(intCode)

    val aInput = mutableListOf(phaseSequence[0], 0)
    val bInput = mutableListOf(phaseSequence[1])
    val cInput = mutableListOf(phaseSequence[2])
    val dInput = mutableListOf(phaseSequence[3])
    val eInput = mutableListOf(phaseSequence[4])

//    println("ampA is ${ampA.hashCode()}")
//    println("ampB is ${ampB.hashCode()}")
//    println("ampC is ${ampC.hashCode()}")
//    println("ampD is ${ampD.hashCode()}")
//    println("ampE is ${ampE.hashCode()}")

    // TODO IO als Deque o.ä.?

    val c1 = CompletableFuture.supplyAsync { ampA.run(input = aInput, output = bInput) }
        .thenRunAsync { ampB.run(input = bInput, output = cInput) }
        .thenRunAsync { ampC.run(input = cInput, output = dInput) }
        .thenRunAsync { ampD.run(input = dInput, output = eInput) }
        .thenRun { ampE.run(input = eInput, output = aInput) }

    while (!c1.isDone) {
        Thread.sleep(100)
    }
    val result = aInput.last()
    println("thrust calculated: $result")
    return result
}


const val day07input =
    """3,8,1001,8,10,8,105,1,0,0,21,34,55,68,85,106,187,268,349,430,99999,3,9,1001,9,5,9,1002,9,5,9,4,9,99,3,9,1002,9,2,9,1001,9,2,9,1002,9,5,9,1001,9,2,9,4,9,99,3,9,101,3,9,9,102,3,9,9,4,9,99,3,9,1002,9,5,9,101,3,9,9,102,5,9,9,4,9,99,3,9,1002,9,4,9,1001,9,2,9,102,3,9,9,101,3,9,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99"""

// max signal: 43210
const val testInput1 = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"
// max singal: 54321
const val testInput2 = "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0"
// max signal: 65210
const val testInput3 =
    "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"
