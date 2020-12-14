package day14

import org.paukov.combinatorics3.Generator
import util.readFile

class Day14(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Long = ValueMutatingProgram().processInstructions(lines).sumOfValuesInMemory()

    fun part2(): Long = AddressMutatingProgram().processInstructions(lines).sumOfValuesInMemory()
}

fun interface Mutator {
    fun applyTo(value: Long): Long
    fun andThen(after: Mutator): Mutator = Mutator { v: Long -> after.applyTo(this.applyTo(v)) }
}

data class OrMask(val mask: Long) : Mutator {
    override fun applyTo(value: Long): Long = value.or(mask)
}

data class AndMask(val mask: Long) : Mutator {
    override fun applyTo(value: Long): Long = value.and(mask)
}

sealed class Program {
    private val memory = mutableMapOf<Long,Long>()
    private val keyValueRegex = """^([^\s]+) = ([^\s]+)${'$'}""".toRegex()
    private val memRegex = """^mem\[(\d+)\]${'$'}""".toRegex()
    fun sumOfValuesInMemory(): Long = memory.values.sum()
    fun processInstructions(instructions: Iterable<String>): Program = instructions.forEach(this::processInstruction).let { return this }
    private fun processInstruction(instruction: String) {
        val (key, value) = keyValueRegex.matchEntire(instruction)!!.destructured
        when(key) {
            "mask" -> setMask(value)
            else -> {
                val (address) = memRegex.matchEntire(key)!!.destructured
                processMemoryInstruction(address.toLong(), value.toLong(), memory)
            }
        }
    }

    abstract fun processMemoryInstruction(address: Long, value: Long, memory: MutableMap<Long, Long>)
    abstract fun setMask(value: String)
    fun orMask(mask: String): Mutator = OrMask(mask.replace('X', '0').toLong(2))
}

data class ValueMutatingProgram(var valueMutator: Mutator = Mutator { v: Long -> v}) : Program() {
    override fun processMemoryInstruction(address: Long, value: Long, memory: MutableMap<Long, Long>) {
        memory[address] = this.valueMutator.applyTo(value)
    }

    override fun setMask(value: String) {
        valueMutator = orMask(value).andThen(andMask(value))
    }

    private fun andMask(mask: String): Mutator = AndMask(mask.replace('X', '1').toLong(2))
}

data class AddressMutatingProgram(var addressMutators: List<Mutator> = listOf()) : Program() {
    override fun processMemoryInstruction(address: Long, value: Long, memory: MutableMap<Long, Long>) = addressMutators.map { it.applyTo(address) }.forEach { memory[it] = value}

    override fun setMask(value: String) {
        val initialMask = orMask(value).andThen(andMask(value))
        this.addressMutators = Generator.subset(orMasks(value))
                .simple()
                .map { masks ->
                    masks.fold(initialMask) { acc, mask ->
                        acc.andThen(mask)
                    }
                }
    }

    private fun andMask(mask: String): Mutator = AndMask(mask.replace('0', '1').replace('X', '0').toLong(2))
    private fun orMasks(mask: String): List<Mutator> = mask.reversed().mapIndexedNotNull { index, char -> if (char == 'X') OrMask(1L shl index) else null }
}