package de.dikodam.adventofcode2019.utils

import de.dikodam.adventofcode2019.utils.IntcodeComputer.OpCode.*
import de.dikodam.adventofcode2019.utils.IntcodeComputer.ParameterMode.IMMEDIATE
import de.dikodam.adventofcode2019.utils.IntcodeComputer.ParameterMode.POSITION

class IntcodeComputer(private var memory: IntArray, private val input: Int) {

    fun run(): List<Int> {
        var ip = 0      // instruction pointer
        var terminated = false
        var output = mutableListOf<Int>()

        while (!terminated) {
            val instruction = parseInstruction(memory, ip)

            val param = { i: Int -> memory[ip + i] }

            when (instruction.opcode) {
                ADD -> {
                    val p1 = if (instruction.modes[0] == IMMEDIATE) param(1) else memory[param(1)]
                    val p2 = if (instruction.modes[1] == IMMEDIATE) param(2) else memory[param(2)]
                    memory[param(3)] = p1 + p2
                }
                MULT -> {
                    val p1 = if (instruction.modes[0] == IMMEDIATE) param(1) else memory[param(1)]
                    val p2 = if (instruction.modes[1] == IMMEDIATE) param(2) else memory[param(2)]
                    memory[param(3)] = p1 * p2
                }
                IN -> {
                    val p1 = param(1)
                    memory[p1] = input
                }
                OUT -> {
                    if (instruction.modes[0] == IMMEDIATE) output.add(param(1)) else output.add(memory[param(1)])
                }
                END -> {
                    terminated = true
                }
            }

            ip += instruction.opcode.length
        }
        return output
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
        // TODO operator invoke returning new ip (and new memory?)
    }

}
