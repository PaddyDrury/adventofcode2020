package day3

import util.readFile

class Day3(private val inputFile: String) {
    private val grid: List<String> = readFile(inputFile)

    data class Slope(val right: Int, val down: Int)

    fun part1(): Int = countTrees(grid, Slope(right = 3, down = 1))

    fun part2(): Long {
        return listOf(
                Slope(1, 1),
                Slope(3, 1),
                Slope(5, 1),
                Slope(7, 1),
                Slope(1, 2)
        ).map {
            countTrees(grid, it)
        }.fold(1L) { acc, i -> acc * i }
    }

    fun countTrees(grid: List<String>, slope: Slope): Int {
        return grid.filterIndexed { idx, value ->
            idx % slope.down == 0
        }.filterIndexed { idx, row ->
            row[slope.right * idx % row.length] == '#'
        }.count()
    }

}