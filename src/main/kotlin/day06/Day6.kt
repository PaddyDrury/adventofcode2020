package day06

import util.readFile
import util.splitWhen

class Day6(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Long = lines.splitWhen { it.isBlank() }.map { it.joinToString(separator = "") }.map { it.countDistinct() }.sum()
    fun part2(): Int = lines.splitWhen { it.isBlank() }.map { it.intersectingChars().size }.sum()
}

fun Iterable<String>.intersectingChars(): Set<Char> = this.fold(this.first().toSet()) { acc, s -> acc.intersect(s.toSet()) }
fun String.countDistinct(): Long = this.chars().distinct().count()