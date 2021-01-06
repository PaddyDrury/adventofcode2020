package day20


import util.chunkWhen
import util.grid.*
import util.readFile
import util.simpleCombinations


class Day20(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val tileRegex = """^Tile (\d+):$""".toRegex()
    private val tiles = lines.chunkWhen(String::isBlank).map {
        val (num) = tileRegex.matchEntire(it.first())!!.destructured
        Tile(num.toInt(), it.drop(1).orientations(), it.drop(1).allSidesForAllOrientations())
    }
    private val matchingCombos = tiles.simpleCombinations(2)
        .filter { it.first().allPossibleSides.intersect(it.last().allPossibleSides).isNotEmpty() }
    private val adjacentTileMappings = tiles.associateWith { tile ->
        matchingCombos.filter { combo -> combo.contains(tile) }.flatMap { combo -> combo.filter { it != tile } }
    }
    private val seaMonster = listOf(
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   "
    )
    private val monsterMap = seaMonster.flatMapIndexed { rowNum, row ->
        row.withIndex().filter { it.value == '#' }.map { Pair(it.index, rowNum) }
    }

    fun isMonsterAt(column: Int, row: Int, grid: Grid) =
        monsterMap.all { grid[row + it.second][column + it.first] == '#' }

    fun part1(): Long =
        adjacentTileMappings.filter { it.value.size == 2 }.map { it.key.id }.fold(1L) { acc, i -> acc * i }

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
            if (unmatchedTiles.isNotEmpty()) {
                image.add(mutableListOf(nextTileAndGridBelow(image.last().first(), unmatchedTiles)))
            }
        } while (unmatchedTiles.isNotEmpty())

        val assembled = image.map { it.map { p -> p.second.removeBorder() } }.merge()

        return assembled.count('#') -
                (assembled.orientations().map { grid ->
                    grid.indices.toList().dropLast(seaMonster.size - 1)
                        .flatMap { row ->
                            grid.first().indices.toList().dropLast(seaMonster.first().length - 1)
                                .map { col -> Pair(col, row) }
                        }.count { isMonsterAt(it.first, it.second, grid) }
                }.maxOrNull()!! * seaMonster.count('#'))
    }

    private fun nextTileAndGridBelow(
        tile: Pair<Tile, Grid>,
        unmatchedTiles: MutableSet<Tile>
    ): Pair<Tile, List<String>> =
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
}

data class Tile(val id: Int, val orientations: List<Grid>, val allPossibleSides: List<String>)