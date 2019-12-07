package de.dikodam.adventofcode2019.utils

import de.dikodam.adventofcode2019.utils.IntcodeComputer.OpCode.*
import de.dikodam.adventofcode2019.utils.IntcodeComputer.ParameterMode.IMMEDIATE
import de.dikodam.adventofcode2019.utils.IntcodeComputer.ParameterMode.POSITION

class IntcodeComputer(private var initialMemory: IntArray) {

    fun run(input: Int): List<Int> {
        var memory = initialMemory.clone()
        var ip = 0      // instruction pointer
        var output = mutableListOf<Int>()

        while (true) {
            val instruction = parseInstruction(memory, ip)
            ip = instruction(memory, ip, input, output) ?: return output
        }
    }

    // instruction format: ABCDE: DE - opcode, A, B, C - modes of 3rd, 2nd, 1st params
    // A should always be 0!
    // leading 0s ignored!
    private fun parseInstruction(memory: IntArray, ip: Int): Instruction {
        val instructionCode = memory[ip]

        val modes = instructionCode.toString()
            .padStart(5, '0')
            .take(3)
            .reversed()
            .chunked(1)
            .map { it.toParameterMode() }

        return Instruction((instructionCode % 100).toOpCode(), modes)
    }

    private enum class OpCode(val length: Int) { ADD(4), MULT(4), IN(2), OUT(2), END(1) }

    private fun Int.toOpCode(): OpCode = when (this) {
        1 -> ADD
        2 -> MULT
        3 -> IN
        4 -> OUT
        99 -> END
        else -> error("invalid opcode: $this")
    }

    // 0 is POSITION, 1 is IMMEDIATE
    private enum class ParameterMode {

        POSITION, IMMEDIATE
    }

    private fun String.toParameterMode(): ParameterMode =
        when (this) {
            "0" -> POSITION
            "1" -> IMMEDIATE
            else -> error("invalid parameter mode: $this")
        }

    private data class Instruction(val opcode: OpCode, val modes: List<ParameterMode>) {
        operator fun invoke(
            memory: IntArray,
            ip: Int,
            input: Int,
            output: MutableList<Int>
        ): Int? {
            fun getParam(i: Int) = if (modes[i - 1] == IMMEDIATE) memory[ip + i] else memory[memory[ip + i]]
            return when (opcode) {
                ADD -> {
                    val p1 = getParam(1)
                    val p2 = getParam(2)
                    val p3 = memory[ip + 3]
                    memory[p3] = p1 + p2
                    ip + opcode.length
                }
                MULT -> {
                    val p1 = getParam(1)
                    val p2 = getParam(2)
                    val p3 = memory[ip + 3]
                    memory[p3] = p1 * p2
                    ip + opcode.length
                }
                IN -> {
                    val p1 = memory[ip + 1]
                    memory[p1] = input
                    ip + opcode.length
                }
                OUT -> {
                    val p1 = getParam(1)
                    output.add(p1)
                    ip + opcode.length
                }
                END -> null
            }
        }
    }
}
