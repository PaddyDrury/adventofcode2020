package day9

import util.*

class Day9(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Long = findFirstInvalid(25)
    fun part2(): Long = computeEncryptionWeakness(25)

    fun findFirstInvalid(preambleLength: Int): Long = lines.map { it.toLong() }
            .windowed(preambleLength + 1)
            .first { window ->
                window.take(preambleLength)
                        .combinations(2)
                        .none { combo ->
                            combo.sumsToValue(window.last())
                        }
            }
            .last()

    fun computeEncryptionWeakness(preambleLength: Int): Long = lines.map { it.toLong() }.findContiguousRangeSummingTo(findFirstInvalid(preambleLength)).addMinToMax()
}

fun List<Long>.findContiguousRangeSummingTo(value: Long) = this.sublistBetweenMinAndMaxOf(
        (this.takeWhile { it < value }.indices)
                .toList()
                .combinations(2)
                .first { this.sublistBetweenMinAndMaxOf(it).sumsToValue(value) }
)
