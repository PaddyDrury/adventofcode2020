package day01

import util.multiCombinations
import util.readFile
import util.sumsToValue

class Day1(private val inputFile: String) {

    private val intList: List<Int>

    init {
        intList = stringsToInts(readFile(inputFile))
    }

    fun stringsToInts(list: List<String>): List<Int> = list.map { it.toInt() }
    fun product(intList: List<Int>): Long = intList.fold(1L) { acc, i -> acc * i }
    fun productOfCombinationSummingTo2020(intList: List<Int>, combinationLength: Int): Long = product(intList.multiCombinations(combinationLength).find { it.sumsToValue(2020) }.orEmpty())
    fun part1(): Long = productOfCombinationSummingTo2020(intList, 2)
    fun part2(): Long = productOfCombinationSummingTo2020(intList, 3)
}

