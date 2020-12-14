package day08

import util.readFile

class Day8(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Int = lines.toInstructions().execute().first

    fun part2(): Int {
        val initialInstructions = lines.toInstructions()
        val initialResult = initialInstructions.execute()
        initialResult.third
                .filter { it.first.isJumpOrNop() }
                .reversed()
                .forEach { item ->
                    val instruction = item.first
                    val instructions = initialInstructions.toMutableList()
                    instructions[instruction.line] = instruction.toggled()
                    val result = instructions.executeFrom(instruction.line, item.second, initialResult.third)
                    if(result.second >= instructions.size) {
                        return result.first
                    }
                }
        error("failed to find way out of program")
    }

    fun List<Instruction>.execute() = executeFrom(0, 0, mutableListOf())

    fun List<Instruction>.executeFrom(startIndex: Int, startValue: Int, history: MutableList<Pair<Instruction, Int>>): Triple<Int, Int, MutableList<Pair<Instruction, Int>>> {
        var index = startIndex
        var value = startValue
        while(index < this.size && !history.any { it.first === this[index]}) {
            history += Pair(this[index], value)
            when(this[index].operation) {
                "nop" -> index++
                "acc" -> value += this[index++].argument
                "jmp" -> index += this[index].argument
            }
        }
        return Triple(value, index, history)
    }
}

data class Instruction(val line: Int, val operation: String,  val argument: Int) {
    fun toggled(): Instruction = Instruction(
            this.line,
            when(this.operation) {
                "jmp" -> "nop"
                "nop" -> "jmp"
                else -> error("blah")
            }, this.argument
    )

    fun isJumpOrNop() = this.operation in listOf("jmp", "nop")
}

fun Iterable<String>.toInstructions(): List<Instruction> = this.mapIndexed { index, line ->
    val(operation, argumentStr) = """^([a-z]+) ([+\-]\d+)${'$'}""".toRegex().matchEntire(line)!!.destructured
    Instruction(index, operation, argumentStr.toInt())
}