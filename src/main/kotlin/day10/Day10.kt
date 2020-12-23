package day10

import util.readFile

class Day10(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Int = findDifferencesOf(1) * findDifferencesOf(3)

    fun part2(): Long = countAdapterConfigurations()

    fun adapters(): List<Long> = lines.asSequence().map(String::toLong)
        .sorted()
        .toList()

    fun findDifferencesOf(value: Long): Int = adapters().toMutableList().let { chain ->
        chain.add(0, 0)
        chain.add(chain.maxOrNull()!! + 3)
        chain.findDifferencesOf(value)
    }

    private fun countAdapterConfigurations(): Long = adapters().let {
        countConfigurationsBetween(
            0,
            it.maxOrNull()!!,
            it,
            mutableMapOf(it.maxOrNull()!! to 1)
        )
    }

    private fun countConfigurationsBetween(
        currentAdapter: Long,
        lastAdapter: Long,
        adapters: List<Long>,
        countCache: MutableMap<Long, Long>
    ): Long =
        countCache[currentAdapter] ?: (currentAdapter + 1L..currentAdapter + 3L)
            .filter(adapters::contains)
            .fold(0L) { acc, adapter ->
                acc + countConfigurationsBetween(adapter, lastAdapter, adapters, countCache)
            }.also { countCache[currentAdapter] = it }
}

fun List<Long>.findDifferencesOf(value: Long): Int = this.sorted()
    .windowed(2)
    .count { (it.last() - it.first()) == value }