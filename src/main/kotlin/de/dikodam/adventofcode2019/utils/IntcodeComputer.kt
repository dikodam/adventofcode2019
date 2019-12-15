package de.dikodam.adventofcode2019.utils

import de.dikodam.adventofcode2019.utils.IntcodeComputer.OpCode.*
import de.dikodam.adventofcode2019.utils.IntcodeComputer.ParameterMode.IMMEDIATE

class IntcodeComputer(private val memory: IntArray) {

    fun run(input: List<Int>): List<Int> {

        var ip = 0      // instruction pointer
        val output = mutableListOf<Int>()

        var inputIndex = 0
        val inputReader = { input[inputIndex++] }

        while (true) {
            val instruction = parseInstruction(memory, ip)
            // println("evaluating expression ${instruction.opcode}${instruction.modes.take(2)} at $ip")
            ip = instruction(memory, ip, inputReader, output) ?: return output
        }
    }

    private fun parseInstruction(memory: IntArray, ip: Int): Instruction {
        val instructionCode = memory[ip]

        val modes = instructionCode.toString()
            .padStart(5, '0')
            .take(3)
            .reversed()
            .chunked(1)
            .map { ParameterMode.parse(it.toInt()) }

        return Instruction(OpCode.parse(instructionCode % 100), modes)
    }

    private enum class OpCode(val length: Int) {
        ADD(4),
        MULT(4),
        IN(2),
        OUT(2),
        JIT(3),
        JIF(3),
        LT(4),
        EQ(4),
        END(1);

        companion object {
            fun parse(code: Int) = when (code) {
                1 -> ADD
                2 -> MULT
                3 -> IN
                4 -> OUT
                5 -> JIT
                6 -> JIF
                7 -> LT
                8 -> EQ
                99 -> END
                else -> error("invalid opcode: $this")
            }
        }
    }

    // 0 is POSITION, 1 is IMMEDIATE
    private enum class ParameterMode {

        POSITION, IMMEDIATE;

        companion object {
            fun parse(code: Int) = when (code) {
                0 -> POSITION
                1 -> IMMEDIATE
                else -> error("invalid parameter mode: $this")
            }

        }
    }

    private data class Instruction(val opcode: OpCode, val modes: List<ParameterMode>) {

        operator fun invoke(
            memory: IntArray,
            ip: Int,
            input: () -> Int,
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
                    memory[p1] = input()
                    ip + opcode.length
                }
                OUT -> {
                    val p1 = getParam(1)
                    output.add(p1)
                    ip + opcode.length
                }
                JIT -> if (getParam(1) != 0) getParam(2) else ip + opcode.length
                JIF -> if (getParam(1) == 0) getParam(2) else ip + opcode.length
                LT -> {
                    val p1 = getParam(1)
                    val p2 = getParam(2)
                    val p3 = memory[ip + 3]
                    memory[p3] = if (p1 < p2) 1 else 0
                    ip + opcode.length
                }
                EQ -> {
                    val p1 = getParam(1)
                    val p2 = getParam(2)
                    val p3 = memory[ip + 3]
                    memory[p3] = if (p1 == p2) 1 else 0
                    ip + opcode.length
                }
                END -> null
            }
        }
    }
}
