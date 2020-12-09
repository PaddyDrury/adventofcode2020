package day9


import com.google.common.collect.EvictingQueue
import util.addMinToMax
import util.combinations
import util.readFile
import util.sumsToValue

class Day9(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Long = findFirstInvalid(25)
    fun part2(): Long = computeEncryptionWeakness(25)

    fun findFirstInvalid(preambleLength: Int): Long {
        lines.map { it.toLong() }.fold(EvictingQueue.create<Long>(preambleLength)) { acc, t ->
            if (acc.remainingCapacity() == 0 && acc.combinations(2).none { it.sumsToValue(t) }) {
                return t
            }
            acc.add(t)
            acc
        }
        error("couldn't find invalid number")
    }

    fun computeEncryptionWeakness(preambleLength: Int): Long =
        findContiguousRangeSummingTo(findFirstInvalid(preambleLength)).addMinToMax()

    fun findContiguousRangeSummingTo(value: Long): List<Long> {
        val preRange = lines.map { it.toLong( )}.takeWhile { it < value }
        val rangeIndices = (preRange.indices).toList()
                .combinations(2)
                .first { preRange.slice(it.minOrNull()!!..it.maxOrNull()!!).sumsToValue(value) }

        return preRange.slice(rangeIndices.minOrNull()!!..rangeIndices.maxOrNull()!!)
    }
}
