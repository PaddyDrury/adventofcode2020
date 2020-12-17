package day17

import util.cartesianProduct
import util.readFile

class Day17(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Int = parseCubes().sequence().take(6).last().activeCubes.count()
    fun part2(): Int = parseTesseracts().sequence().take(6).last().activeCubes.count()

    private fun parseCubes(): ConwayCubes<CubeCoords> = ConwayCubes(lines.flatMapIndexed { y, line ->
        line.withIndex().filter { it.value == '#' }.map { CubeCoords(it.index, y, 0) }
    }.toSet())

    private fun parseTesseracts(): ConwayCubes<TesseractCoords> = ConwayCubes(lines.flatMapIndexed { y, line ->
        line.withIndex().filter { it.value == '#' }.map { TesseractCoords(it.index, y, 0, 0) }
    }.toSet())
}

data class ConwayCubes<T: Coords<T>>(val activeCubes: Set<T>) {
    private fun nextIteration(): ConwayCubes<T> = ConwayCubes(
            (activeCubes.flatMap(Coords<T>::neighbours).toSet() + activeCubes).filter {
                val activeNeighbourCount = it.neighbours().count(activeCubes::contains)
                activeNeighbourCount == 3 || (activeNeighbourCount == 2 && activeCubes.contains(it))
            }.toSet()
    )

    fun sequence(): Sequence<ConwayCubes<T>> = generateSequence(nextIteration(), { it.nextIteration() })
}


interface Coords<T> where T: Coords<T>{
    fun neighbours(): Set<T>
}

data class CubeCoords(val x: Int, val y: Int, val z: Int): Coords<CubeCoords> {
    override fun neighbours(): Set<CubeCoords> = cartesianProduct(
            x - 1..x + 1,
            y - 1..y + 1,
            z - 1..z + 1
    ).map {
        CubeCoords(it[0], it[1], it[2])
    }.filter { it != this }.toSet()
}

data class TesseractCoords(val x: Int, val y: Int, val z: Int, val w: Int): Coords<TesseractCoords> {
    override fun neighbours(): Set<TesseractCoords> = cartesianProduct(
            x - 1..x + 1,
            y - 1..y + 1,
            z - 1..z + 1,
            w - 1..w + 1
    ).map {
        TesseractCoords(it[0], it[1], it[2], it[3])
    }.filter { it != this }.toSet()
}