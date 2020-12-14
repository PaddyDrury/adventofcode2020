package day10

import util.readFile

class Day10(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Int = findDifferencesOf(1) * findDifferencesOf(3)

    fun part2(): Long = countAdapterConfigurations()

    fun adapters(): List<Long> = lines.asSequence().map(String::toLong)
            .sorted()
            .toList()

    fun findDifferencesOf(value: Long): Int {
        val chain = adapters().toMutableList()
        chain.add(0, 0)
        chain.add(chain.maxOrNull()!! + 3)
        return chain.findDifferencesOf(value)
    }

    fun countAdapterConfigurations(): Long {
        val adapters = adapters()
        return memoisedCountConfigurationsBetween(0, adapters.maxOrNull()!!, adapters, mutableMapOf(adapters.maxOrNull()!! to 1))
    }

    fun memoisedCountConfigurationsBetween(currentAdapter: Long, lastAdapter: Long, adapters: List<Long>, countCache: MutableMap<Long, Long>): Long {
        countCache[currentAdapter] = countCache[currentAdapter] ?: countConfigurationsBetween(currentAdapter, lastAdapter, adapters, countCache)
        return countCache[currentAdapter]!!
    }

    private fun countConfigurationsBetween(currentAdapter: Long, lastAdapter: Long, adapters: List<Long>, countCache: MutableMap<Long, Long>) =
            (currentAdapter + 1L..currentAdapter + 3L)
                    .filter(adapters::contains)
                    .fold(0L) { acc, adapter ->
                        acc + memoisedCountConfigurationsBetween(adapter, lastAdapter, adapters, countCache)
                    }
}

fun List<Long>.findDifferencesOf(value: Long): Int = this.sorted()
        .windowed(2)
        .count { (it.last() - it.first()) == value }