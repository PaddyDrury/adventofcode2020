package day24

import util.readFile

class Day24(inputFile: String) {
    private val lines = readFile(inputFile)
    private val regex = """(sw|se|nw|ne|w|e)""".toRegex()

    fun part1(): Int = initialBlackTiles().size

    fun part2(): Int = blackTileSequence().drop(99).take(1).first().count()

    private fun initialBlackTiles(): Set<HexagonCoords> =
        lines.map(::parseLine).groupingBy { it }.eachCount().filter { it.value % 2 == 1 }.keys

    fun blackTileSequence(): Sequence<Set<HexagonCoords>> =
        generateSequence(nextTileLayout(initialBlackTiles())) { nextTileLayout(it) }

    private fun nextTileLayout(blackTiles: Set<HexagonCoords>): Set<HexagonCoords> {
        val tilesToFlipToWhite = blackTiles.filterNot { tile -> tile.neighbours().count { it in blackTiles } in 1..2 }
        val tilesToFlipToBlack = blackTiles.flatMap { it.neighbours() }.distinct().filterNot { it in blackTiles }
            .filter { tile -> tile.neighbours().count { it in blackTiles } == 2 }
        return blackTiles - tilesToFlipToWhite + tilesToFlipToBlack
    }

    private fun parseLine(line: String): HexagonCoords =
        generateSequence(regex.find(line)) {
            regex.find(line, it.range.last + 1)
        }.map {
            it.value
        }.fold(HexagonCoords(0, 0)) { acc, direction ->
            acc.move(direction)
        }

}

data class HexagonCoords(val e: Int, val ne: Int) {
    fun move(direction: String) = when (direction) {
        "sw" -> this.move(ne = -1)
        "se" -> this.move(1, -1)
        "nw" -> this.move(-1, 1)
        "ne" -> this.move(ne = 1)
        "e" -> this.move(e = 1)
        "w" -> this.move(e = -1)
        else -> error("Invalid direction $direction")
    }

    private fun move(e: Int = 0, ne: Int = 0) = HexagonCoords(this.e + e, this.ne + ne)
    fun neighbours() = sequenceOf("sw", "se", "nw", "ne", "w", "e").map(::move).toSet()
}