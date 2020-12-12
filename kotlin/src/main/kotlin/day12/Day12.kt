package day12

import util.readFile
import kotlin.math.abs

class Day12(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val startingCoords = Coords(0, 0)
    private val startingPositionDirect = PositionDirect(startingCoords, 90)
    private val startingPositionWaypoint = PositionWaypoint(startingCoords, Coords(10, 1))
    private val lineRegex = """^([NSEWRLF])(\d+)${'$'}""".toRegex()

    data class Coords(val x: Int, val y: Int) {
        fun goEast(distance: Int): Coords = Coords(x + distance, y)
        fun goNorth(distance: Int): Coords = Coords(x, y + distance)

        fun rotate(degrees: Int): Coords =
                when (degrees) {
                    90, -270 -> Coords(y, -x)
                    270, -90 -> Coords(-y, x)
                    180, -180 -> Coords(-x, -y)
                    else -> error("Invalid rotation")
                }

        fun moveBy(coords: Coords, times: Int): Coords = Coords(x + (coords.x * times), y + (coords.y * times))

        fun manhattanDistanceFrom(here: Coords): Int = abs(x - here.x) + abs(y - here.y)
    }

    abstract class Position<T> where T : Position<T> {
        abstract val coords: Coords
        abstract fun east(distance: Int): T
        fun west(distance: Int): T = east(-distance)
        abstract fun north(distance: Int): T
        fun south(distance: Int): T = north(-distance)
        abstract fun right(degrees: Int): T
        fun left(degrees: Int): T = right(-degrees)
        abstract fun forward(distance: Int): T
    }

    data class PositionWaypoint(override val coords: Coords, val waypoint: Coords) : Position<PositionWaypoint>() {
        override fun east(distance: Int): PositionWaypoint = PositionWaypoint(coords, waypoint.goEast(distance))
        override fun north(distance: Int): PositionWaypoint = PositionWaypoint(coords, waypoint.goNorth(distance))
        override fun right(degrees: Int): PositionWaypoint = PositionWaypoint(coords, waypoint.rotate(degrees))
        override fun forward(distance: Int): PositionWaypoint = PositionWaypoint(coords.moveBy(waypoint, distance), waypoint)
    }

    data class PositionDirect(override val coords: Coords, val heading: Int) : Position<PositionDirect>() {
        override fun east(distance: Int): PositionDirect = PositionDirect(coords.goEast(distance), heading)
        override fun north(distance: Int): PositionDirect = PositionDirect(coords.goNorth(distance), heading)
        override fun right(degrees: Int): PositionDirect = PositionDirect(coords, heading + degrees)
        override fun forward(distance: Int): PositionDirect = when (this.heading % 360) {
            0 -> north(distance)
            90, -270 -> east(distance)
            180, -180 -> south(distance)
            270, -90 -> west(distance)
            else -> error("Invalid heading $heading")
        }
    }

    private fun <T> navigateFrom(startingPosition: Position<T>): Position<T> where T : Position<T> = lines
            .asSequence()
            .map(lineRegex::matchEntire)
            .requireNoNulls()
            .map(MatchResult::destructured)
            .map(MatchResult.Destructured::toList)
            .fold(startingPosition) { pos, mr ->
                val instruction = mr.first()
                val arg = mr.last().toInt()
                println(pos)
                when (instruction) {
                    "N" -> pos.north(arg)
                    "E" -> pos.east(arg)
                    "W" -> pos.west(arg)
                    "S" -> pos.south(arg)
                    "R" -> pos.right(arg)
                    "L" -> pos.left(arg)
                    "F" -> pos.forward(arg)
                    else -> error("Invalid instruction")

                }
            }

    fun part1(): Int = navigateFrom(startingPositionDirect).coords.manhattanDistanceFrom(startingCoords)
    fun part2(): Int = navigateFrom(startingPositionWaypoint).coords.manhattanDistanceFrom(startingCoords)
}