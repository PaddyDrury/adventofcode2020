package day1

import org.paukov.combinatorics3.Generator
import util.readFile
import java.io.File

class Day1(private val inputFile: String) {

    private val intList: List<Int>

    init {
        intList = stringsToInts(readFile(inputFile))
    }

    fun stringsToInts(list: List<String>): List<Int> = list.map { it.toInt() }
    fun sumsToValue(intList: List<Int>, value: Int): Boolean = intList.sum() == value
    fun combinations(intList: List<Int>, length: Int): Iterable<List<Int>> = Generator.combination(intList).multi(length)
    fun product(intList: List<Int>): Long = intList.fold(1L) { acc, i -> acc * i }
    fun productOfCombinationSummingTo2020(intList: List<Int>, combinationLength: Int): Long = product(combinations(intList, combinationLength).find { sumsToValue(it, 2020) }.orEmpty())
    fun part1(): Long = productOfCombinationSummingTo2020(intList, 2)
    fun part2(): Long = productOfCombinationSummingTo2020(intList, 3)
}