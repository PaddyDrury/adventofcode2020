package day20

import util.combinations
import util.readFile
import util.splitWhen

class Day20(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val tileRegex = """^Tile (\d+):$""".toRegex()
    private val tiles = lines.splitWhen(String::isBlank).map {
        val (num) = tileRegex.matchEntire(it.first())!!.destructured
        Tile(num.toInt(), it.drop(1).orientations(), it.drop(1).allPossibleSides())
    }

    fun part1(): Long {
        val matchingCombos = tiles.combinations(2).filter{ it.first().allPossibleSides.intersect(it.last().allPossibleSides).isNotEmpty() }
        return tiles.groupBy { tile -> matchingCombos.count { it.contains(tile) } }.minByOrNull { it.key }!!.value.fold(1L) { acc, it -> acc * it.id}
    }
}

data class Tile(val id: Int, val orientations: List<Grid>, val allPossibleSides: List<String>) {
}

data class Coords(val x: Int, val y: Int) {
    fun right(): Coords = Coords(x + 1, y)
    fun down(): Coords = Coords(x, y + 1)
}

typealias Grid = List<String>

fun Grid.transpose(): Grid = this.indices.map { idx -> String(this.map { it[idx] }.toCharArray()) }
fun Grid.flipHorizontal(): Grid = this.map(String::reversed)
fun Grid.rotate(): Grid = this.transpose().flipHorizontal()
fun Grid.leftSide(): String = this.map { it.first() }.toString()
fun Grid.rightSide(): String = this.map { it.last() }.toString()
fun Grid.top(): String = this.first()
fun Grid.bottom(): String = this.last()
fun Grid.matchesRight(rightHandGrid: Grid) = this.rightSide() == rightHandGrid.leftSide()
fun Grid.matchesBelow(belowGrid: Grid) = this.bottom() == belowGrid.top()
fun Grid.print() = this.forEach(::println)
fun Grid.orientations(): List<Grid> = listOf(
    this,
    this.rotate(),
    this.rotate().rotate(),
    this.rotate().rotate().rotate(),
    this.flipHorizontal(),
    this.flipHorizontal().rotate(),
    this.flipHorizontal().rotate().rotate(),
    this.flipHorizontal().rotate().rotate().rotate(),
)
fun Grid.sides(): List<String> = listOf(this.leftSide(), this.rightSide(), this.top(), this.bottom())
fun Grid.allPossibleSides(): List<String> = this.orientations().flatMap { it.sides() }