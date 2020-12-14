package day14

import org.paukov.combinatorics3.Generator
import util.readFile

class Day14(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val maskRegex = """^mask = ([01X]+)${'$'}""".toRegex()
    private val memRegex = """^mem\[(\d+)\] = (\d+)${'$'}""".toRegex()

    fun part1(): Long = lines.fold(Pair(OrMask(0L) as Mask, mutableMapOf<Long, Long>())) { acc, line ->
        when {
            maskRegex.matches(line) -> Pair(maskRegex.matchEntire(line)!!.destructured.component1().toMasks(), acc.second)
            memRegex.matches(line) -> {
                val (address, value) = memRegex.matchEntire(line)!!.destructured
                acc.second[address.toLong()] = acc.first.applyTo(value.toLong())
                acc
            }
            else -> error("invalid line")
        }
    }.second.values.sum()


    fun part2(): Long = lines.fold(Pair(Pair(OrMask(0L) as Mask, listOf<OrMask>()), mutableMapOf<Long, Long>())) { acc, line ->
        when {
            maskRegex.matches(line) -> Pair(maskRegex.matchEntire(line)!!.destructured.component1().toFloatingBitsMasks(), acc.second)
            memRegex.matches(line) -> {
                val (address, value) = memRegex.matchEntire(line)!!.destructured
                acc.first.findPossibleValuesfor(address.toLong()).forEach { acc.second[it] = value.toLong() }
                acc
            }
            else -> error("invalid line")
        }
    }.second.values.sum()
}

fun interface Mask {
    fun applyTo(value: Long): Long

    fun andThen(after: Mask): Mask = Mask { v: Long -> after.applyTo(this.applyTo(v)) }
}

data class OrMask(val mask: Long) : Mask {
    override fun applyTo(value: Long): Long = value.or(mask)
}

data class AndMask(val mask: Long) : Mask {
    override fun applyTo(value: Long): Long = value.and(mask)
}

fun String.toMasks(): Mask = this.toOrMask().andThen(this.toAndMask())
fun String.toOrMask(): OrMask = OrMask(this.replace('X', '0').toLong(2))
fun String.toAndMask(): AndMask = AndMask(this.replace('X', '1').toLong(2))

fun String.toFloatingBitsMasks(): Pair<Mask, List<OrMask>> = Pair(this.toOrMask().andThen(this.floatingBitsToAndMask()), this.floatingBitsToOrMasks())
fun String.floatingBitsToAndMask(): AndMask = AndMask(this.replace('0', '1').replace('X', '0').toLong(2))
fun String.floatingBitsToOrMasks(): List<OrMask> = this.reversed().mapIndexedNotNull { index, char -> if (char == 'X') OrMask(1L shl index) else null }

fun Pair<Mask, List<OrMask>>.findPossibleValuesfor(value: Long): List<Long> = Generator
        .subset(this.second)
        .simple()
        .map { masks ->
            masks.fold(first) { acc, mask ->
                acc.andThen(mask)
            }
        }.map { it.applyTo(value) }
