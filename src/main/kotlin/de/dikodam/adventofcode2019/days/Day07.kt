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

    // pause at OUT, current state {PAUSED, TERMINATED} as public readonly variable



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


private const val day07input =
    """3,8,1001,8,10,8,105,1,0,0,21,34,55,68,85,106,187,268,349,430,99999,3,9,1001,9,5,9,1002,9,5,9,4,9,99,3,9,1002,9,2,9,1001,9,2,9,1002,9,5,9,1001,9,2,9,4,9,99,3,9,101,3,9,9,102,3,9,9,4,9,99,3,9,1002,9,5,9,101,3,9,9,102,5,9,9,4,9,99,3,9,1002,9,4,9,1001,9,2,9,102,3,9,9,101,3,9,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99"""
