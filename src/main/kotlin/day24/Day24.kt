package day24

import util.readFile

class Day24(inputFile: String) {
    private val lines = readFile(inputFile)
    private val regex = """(sw|se|nw|ne|w|e)""".toRegex()

    fun part1(): Int = lines.map(::parseLine).groupingBy { it }.eachCount().filter { it.value % 2 == 1 }.size

    fun parseLine(line: String): Coords =
        generateSequence(regex.find(line)) {
            regex.find(line, it.range.last + 1)
        }.map {
            it.value
        }.fold(Coords(0, 0)) { acc, direction ->
            when (direction) {
                "sw" -> acc.move(ne = -1)
                "se" -> acc.move(1, -1)
                "nw" -> acc.move(-1, 1)
                "ne" -> acc.move(ne = 1)
                "e" -> acc.move(e = 1)
                "w" -> acc.move(e = -1)
                else -> error("Invalid direction $direction")
            }
        }

}

data class Coords(val e: Int, val ne: Int) {
    fun move(e: Int = 0, ne: Int = 0) = Coords(this.e + e, this.ne + ne)
}