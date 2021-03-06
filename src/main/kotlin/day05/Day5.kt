package day05

import util.readFile
import java.math.BigDecimal
import java.math.RoundingMode

class Day5(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Long = lines.maxOfOrNull(::binarySeatId)!!
    fun part2(): Long = lines.map(::binarySeatId).findFirstMissing()
    fun seatId(seatCode: String): Long = (row(seatCode) * 8L) + column(seatCode)
    fun binarySeatId(seatCode: String): Long = seatCode
            .replace("[BR]".toRegex(), "1")
            .replace("[FL]".toRegex(), "0")
            .toLong(2)
    fun row(seatCode: String): Int = seatCode.converge(Pair(0, 127), 'B', 'F') ?: error("blah")
    fun column(seatCode: String): Int = seatCode.converge(Pair(0, 7), 'R', 'L') ?: error("blah")
}

fun CharSequence.converge(range: Pair<Int, Int>, takeUpper: Char, takeLower: Char): Int? =
        this.fold(range) { acc, t ->
            if (takeLower == t) {
                Pair(acc.first, midPoint(acc.first, acc.second, RoundingMode.FLOOR))
            } else if (takeUpper == t) {
                Pair(midPoint(acc.first, acc.second, RoundingMode.CEILING), acc.second)
            } else {
                acc
            }
        }.getIfEqual()

fun Iterable<Long>.findFirstMissing(): Long = this.sortedBy { it }.reduce { acc, l -> if (acc + 1 < l) return acc + 1 else l }

fun <T> Pair<T, T>.getIfEqual(): T? = if (this.first?.equals(this.second) == true) this.first else null

fun midPoint(x: Int, y: Int, roundingMode: RoundingMode): Int = BigDecimal(x.toLong() + y).divide(BigDecimal(2), roundingMode).intValueExact()
