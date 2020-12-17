package day17

import org.paukov.combinatorics3.Generator
import org.paukov.combinatorics3.IGenerator
import util.readFile

class Day17(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Int = parseCubes().sequence().take(6).last().activeCubes.count()
    fun part2(): Int = parseTesseracts().sequence().take(6).last().activeTesseracts.count()

    fun parseCubes(): ConwayCubes = ConwayCubes(lines.flatMapIndexed { y, line ->
        line.withIndex().filter { it.value== '#' }.map { CubeCoords(it.index, y, 0) }
    }.toSet())

    fun parseTesseracts(): ConwayTesseracts = ConwayTesseracts(lines.flatMapIndexed { y, line ->
        line.withIndex().filter { it.value== '#' }.map { TesseractCoords(it.index, y, 0, 0) }
    }.toSet())
}

data class ConwayTesseracts(val activeTesseracts: Set<TesseractCoords>) {
    fun nextIteration() = ConwayTesseracts(cartesianProduct(
            minX()-1..maxX()+1,
            minY()-1..maxY()+1,
            minZ()-1..maxZ()+1,
            minW()-1..maxW()+1
    ).map {
        TesseractCoords(it[0], it[1], it[2], it[3])
    }.filter {
        val activeNeighbourCount = it.neighbours().count(activeTesseracts::contains)
        activeNeighbourCount == 3 || (activeNeighbourCount == 2 && activeTesseracts.contains(it))
    }.toSet())

    fun sequence(): Sequence<ConwayTesseracts> = generateSequence(nextIteration(), {it.nextIteration()})

    fun minX(): Int = activeTesseracts.minOfOrNull { it.x }!!
    fun minY(): Int = activeTesseracts.minOfOrNull { it.y }!!
    fun minZ(): Int = activeTesseracts.minOfOrNull { it.z }!!
    fun minW(): Int = activeTesseracts.minOfOrNull { it.w }!!
    fun maxX(): Int = activeTesseracts.maxOfOrNull { it.x }!!
    fun maxY(): Int = activeTesseracts.maxOfOrNull { it.y }!!
    fun maxZ(): Int = activeTesseracts.maxOfOrNull { it.z }!!
    fun maxW(): Int = activeTesseracts.maxOfOrNull { it.w }!!
}

data class ConwayCubes(val activeCubes: Set<CubeCoords>) {
    fun nextIteration() = ConwayCubes(cartesianProduct(
            minX()-1..maxX()+1,
            minY()-1..maxY()+1,
            minZ()-1..maxZ()+1
    ).map {
        CubeCoords(it[0], it[1], it[2])
    }.filter {
        val activeNeighbourCount = it.neighbours().count(activeCubes::contains)
        activeNeighbourCount == 3 || (activeNeighbourCount == 2 && activeCubes.contains(it))
    }.toSet())

    fun sequence(): Sequence<ConwayCubes> = generateSequence(nextIteration(), {it.nextIteration()})

    fun minX(): Int = activeCubes.minOfOrNull { it.x }!!
    fun minY(): Int = activeCubes.minOfOrNull { it.y }!!
    fun minZ(): Int = activeCubes.minOfOrNull { it.z }!!
    fun maxX(): Int = activeCubes.maxOfOrNull { it.x }!!
    fun maxY(): Int = activeCubes.maxOfOrNull { it.y }!!
    fun maxZ(): Int = activeCubes.maxOfOrNull { it.z }!!
}



data class CubeCoords(val x: Int, val y: Int, val z: Int) {
    fun neighbours(): Set<CubeCoords> = cartesianProduct(
            x-1..x+1,
            y-1..y+1,
            z-1..z+1
    ).map {
        CubeCoords(it[0], it[1], it[2])
    }.filter { it != this }.toSet()
}

data class TesseractCoords(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun neighbours(): Set<TesseractCoords> = cartesianProduct(
            x-1..x+1,
            y-1..y+1,
            z-1..z+1,
            w-1..w+1
    ).map {
        TesseractCoords(it[0], it[1], it[2], it[3])
    }.filter { it != this }.toSet()
}

fun cartesianProduct(vararg range: Iterable<Int>): IGenerator<MutableList<Int>> = Generator.cartesianProduct(*(range.map { it.toMutableList() }.toTypedArray()))