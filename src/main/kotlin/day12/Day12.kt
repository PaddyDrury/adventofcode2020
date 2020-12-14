package day12

import util.readFile
import kotlin.math.abs

class Day12(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val startingCoords = Coords(0, 0)
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

    abstract class Navigator<T> where T : Navigator<T> {
        abstract val coords: Coords
        abstract fun navigateEast(distance: Int): T
        fun navigateWest(distance: Int): T = navigateEast(-distance)
        abstract fun navigateNorth(distance: Int): T
        fun navigateSouth(distance: Int): T = navigateNorth(-distance)
        abstract fun rotateRight(degrees: Int): T
        fun rotateLeft(degrees: Int): T = rotateRight(-degrees)
        abstract fun goForward(distance: Int): T
    }

    data class WaypointNavigator(override val coords: Coords, val waypoint: Coords) : Navigator<WaypointNavigator>() {
        override fun navigateEast(distance: Int): WaypointNavigator = WaypointNavigator(coords, waypoint.goEast(distance))
        override fun navigateNorth(distance: Int): WaypointNavigator = WaypointNavigator(coords, waypoint.goNorth(distance))
        override fun rotateRight(degrees: Int): WaypointNavigator = WaypointNavigator(coords, waypoint.rotate(degrees))
        override fun goForward(distance: Int): WaypointNavigator = WaypointNavigator(coords.moveBy(waypoint, distance), waypoint)
    }

    data class DirectNavigator(override val coords: Coords, val heading: Int) : Navigator<DirectNavigator>() {
        override fun navigateEast(distance: Int): DirectNavigator = DirectNavigator(coords.goEast(distance), heading)
        override fun navigateNorth(distance: Int): DirectNavigator = DirectNavigator(coords.goNorth(distance), heading)
        override fun rotateRight(degrees: Int): DirectNavigator = DirectNavigator(coords, heading + degrees)
        override fun goForward(distance: Int): DirectNavigator = when (this.heading % 360) {
            0 -> navigateNorth(distance)
            90, -270 -> navigateEast(distance)
            180, -180 -> navigateSouth(distance)
            270, -90 -> navigateWest(distance)
            else -> error("Invalid heading $heading")
        }
    }

    private fun <T> navigateUsing(nav: Navigator<T>): Navigator<T> where T : Navigator<T> = lines
            .asSequence()
            .map(lineRegex::matchEntire)
            .requireNoNulls()
            .map(MatchResult::destructured)
            .map(MatchResult.Destructured::toList)
            .fold(nav) { pos, mr ->
                val instruction = mr.first()
                val arg = mr.last().toInt()
                println(pos)
                when (instruction) {
                    "N" -> pos.navigateNorth(arg)
                    "E" -> pos.navigateEast(arg)
                    "W" -> pos.navigateWest(arg)
                    "S" -> pos.navigateSouth(arg)
                    "R" -> pos.rotateRight(arg)
                    "L" -> pos.rotateLeft(arg)
                    "F" -> pos.goForward(arg)
                    else -> error("Invalid instruction")
                }
            }

    fun part1(): Int = navigateUsing(DirectNavigator(startingCoords, 90)).coords.manhattanDistanceFrom(startingCoords)
    fun part2(): Int = navigateUsing(WaypointNavigator(startingCoords, Coords(10, 1))).coords.manhattanDistanceFrom(startingCoords)
}