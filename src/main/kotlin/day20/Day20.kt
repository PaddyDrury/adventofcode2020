package day20

import util.readFile
import util.simpleCombinations
import util.splitWhen

class Day20(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val tileRegex = """^Tile (\d+):$""".toRegex()
    private val tiles = lines.splitWhen(String::isBlank).map {
        val (num) = tileRegex.matchEntire(it.first())!!.destructured
        Tile(num.toInt(), it.drop(1).orientations(), it.drop(1).allSidesForAllOrientations())
    }
    private val matchingCombos = tiles.simpleCombinations(2)
        .filter { it.first().allPossibleSides.intersect(it.last().allPossibleSides).isNotEmpty() }
    private val adjacentTileMappings = tiles.associateWith { tile ->
        matchingCombos.filter { combo -> combo.contains(tile) }.flatMap { combo -> combo.filter { it != tile } }
    }

    fun part1(): Long = adjacentTileMappings.filter { it.value.size == 2 }.map { it.key.id }.fold(1L) { acc, i -> acc * i }


    fun part2(): Int {
        val unmatchedTiles = adjacentTileMappings.keys.toMutableSet()
        val image = mutableListOf(mutableListOf(
            let {
                adjacentTileMappings.entries.first { it.value.size == 2 }
            }.also {
                unmatchedTiles.remove(it.key)
            }.let {
                Pair(
                    it.key,
                    it.key.orientations.first { grid ->
                        it.value.first().orientations.any(grid::matchesBelow) && it.value.last().orientations.any(
                            grid::matchesRight
                        )
                    })
            }
        ))

        do {
            do {
                image.last().add(nextTileAndGridToTheRight(image.last().last(), unmatchedTiles)!!)
            } while (nextTileAndGridToTheRight(image.last().last(), unmatchedTiles) != null)
            if(unmatchedTiles.isNotEmpty()) {
                image.add(mutableListOf(nextTileAndGridBelow(image.last().first(), unmatchedTiles)))
            }
        } while (unmatchedTiles.isNotEmpty())

        val assembled = image.map { it.map { p -> p.second.removeBorder() } }.assemble()

        assembled.print()

        return assembled.countHashes() -
        (assembled.orientations().map { grid ->
            grid.indices.toList().dropLast(seaMonsterHeight -1)
                .flatMap { row ->
                    grid.first().indices.toList().dropLast(seaMonsterWidth - 1).map { col -> Pair(col, row) }
                }.count { isMonsterAt(it.first, it.second, grid) }
        }.max()!! * seaMonster.countHashes())
    }

    private fun nextTileAndGridBelow(tile: Pair<Tile, Grid>, unmatchedTiles: MutableSet<Tile>): Pair<Tile, List<String>> =
        adjacentTileMappings[tile.first]!!.first { it.orientations.any(tile.second::matchesBelow) }
            .let { nextTile -> Pair(nextTile, nextTile.orientations.first(tile.second::matchesBelow)) }
            .also { unmatchedTiles.remove(it.first) }

    private fun nextTileAndGridToTheRight(tile: Pair<Tile, Grid>, unmatchedTiles: MutableSet<Tile>): Pair<Tile, Grid>? {
        val nextTile = adjacentTileMappings[tile.first]!!.firstOrNull { it.orientations.any(tile.second::matchesRight) }
        return if (nextTile != null) Pair(
            nextTile,
            nextTile.orientations.first(tile.second::matchesRight)
        ).also { unmatchedTiles.remove(nextTile) } else null
    }

    val seaMonster = listOf(
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   "
    )

    val monsterMap = seaMonster.flatMapIndexed { rowNum, row ->
        row.withIndex().filter { it.value == '#' }.map { Pair(it.index, rowNum) }
    }

    val seaMonsterHeight = seaMonster.size
    val seaMonsterWidth = seaMonster.first().length

    fun isMonsterAt(x: Int, y: Int, grid: Grid) =
        monsterMap.all { grid[y+it.second][x+it.first] == '#' }
}




data class Tile(val id: Int, val orientations: List<Grid>, val allPossibleSides: List<String>)

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
fun Grid.allSidesForAllOrientations(): List<String> = this.orientations().flatMap { it.sides() }
fun List<List<Grid>>.assemble(): Grid = this.map { it.joinHorizontal() }.joinVertical()
fun List<Grid>.joinHorizontal(): Grid = this.reduce { acc, it -> acc.zip(it).map { it.first + it.second } }
fun List<Grid>.joinVertical() = this.reduce { acc, it -> acc + it }
fun Grid.countHashes() = this.sumOf { it.count { it == '#' } }

fun Grid.removeBorder() = this.drop(1).dropLast(1).map { it.substring(1, it.length - 1) }