package day11

import org.paukov.combinatorics3.Generator
import util.readFile

class Day11(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    enum class SeatState {
        OCCUPIED,
        VACANT
    }

    fun part1(): Int = initialMap().findEquilibriumPart1().countOccupiedSeats()

    fun part2(): Int = initialMap().findEquilibriumPart2().countOccupiedSeats()

    data class SeatOccupancyMap(val seats: Map<SeatCoordinates, SeatState>, val visibleSeatCache: MutableMap<SeatCoordinates, Set<SeatCoordinates>> = mutableMapOf()) {
        fun nextPart1(): SeatOccupancyMap = SeatOccupancyMap(this.seats.mapValues { nextValuePart1(it.key) })

        fun nextPart2(): SeatOccupancyMap = SeatOccupancyMap(this.seats.mapValues { nextValuePart2(it.key) }, this.visibleSeatCache)

        fun nextValuePart2(coordinates: SeatCoordinates): SeatState {
            val visibleSeatOccupancyCount = findVisibleSeatsFrom(coordinates).count {
                this.seats[it]?.equals(SeatState.OCCUPIED) ?: error("Seat should not be null")
            }
            val currentState = seats[coordinates] ?: error("Seat should not be null")
            return if (currentState == SeatState.VACANT && visibleSeatOccupancyCount == 0) SeatState.OCCUPIED else if (currentState == SeatState.OCCUPIED && visibleSeatOccupancyCount >= 5) SeatState.VACANT else currentState
        }

        fun findVisibleSeatsFrom(coordinates: SeatCoordinates): Set<SeatCoordinates> {
            visibleSeatCache[coordinates] = visibleSeatCache[coordinates]
                    ?: coordinates.findVisibleSeats(this.seats.keys)
            return visibleSeatCache[coordinates]!!
        }

        fun nextValuePart1(coordinates: SeatCoordinates): SeatState {
            val adjacentSeatOccupancyCount = coordinates.adjacentSeatCoordinates().count {
                this.seats[it]?.equals(SeatState.OCCUPIED) ?: false
            }
            val currentState = seats[coordinates] ?: error("Seat should not be null")
            return if (currentState == SeatState.VACANT && adjacentSeatOccupancyCount == 0) SeatState.OCCUPIED else if (currentState == SeatState.OCCUPIED && adjacentSeatOccupancyCount >= 4) SeatState.VACANT else currentState
        }

        fun findEquilibriumPart1(): SeatOccupancyMap {
            if (nextPart1() == this) {
                return this
            }
            return nextPart1().findEquilibriumPart1()
        }

        fun findEquilibriumPart2(): SeatOccupancyMap {
            if (nextPart2() == this) {
                return this
            }
            return nextPart2().findEquilibriumPart2()
        }

        fun countOccupiedSeats(): Int = seats.values.count { it == SeatState.OCCUPIED }
    }

    data class SeatCoordinates(val column: Int, val row: Int) {
        fun adjacentSeatCoordinates(): Set<SeatCoordinates> = Generator.cartesianProduct((column - 1..column + 1).toMutableList(),
                (row - 1..row + 1).toMutableList())
                .map { SeatCoordinates(it.first(), it.last()) }
                .filter { it != this }
                .toSet()

        fun findVisibleSeats(seats: Set<SeatCoordinates>): Set<SeatCoordinates> {
            val left = (this.column - 1 downTo 0)
            val right = (this.column + 1..seats.maxOf { it.column })
            val up = (this.row - 1 downTo 0)
            val down = (this.row + 1..seats.maxOf { it.row })

            return setOfNotNull(
                    left.zip(up).map { it.toSeatCoordinates() }.firstOrNull(seats::contains),
                    right.zip(up).map { it.toSeatCoordinates() }.firstOrNull(seats::contains),
                    left.zip(down).map { it.toSeatCoordinates() }.firstOrNull(seats::contains),
                    right.zip(down).map { it.toSeatCoordinates() }.firstOrNull(seats::contains),
                    up.map { SeatCoordinates(this.column, it) }.firstOrNull(seats::contains),
                    down.map { SeatCoordinates(this.column, it) }.firstOrNull(seats::contains),
                    left.map { SeatCoordinates(it, this.row) }.firstOrNull(seats::contains),
                    right.map { SeatCoordinates(it, this.row) }.firstOrNull(seats::contains)
            ).toHashSet()
        }
    }

    fun initialMap(): SeatOccupancyMap = SeatOccupancyMap(lines.flatMapIndexed { rowNumber, row ->
        row.withIndex()
                .filter { it.value == 'L' }
                .map { SeatCoordinates(it.index, rowNumber) }
    }.associateWith { SeatState.VACANT })

}

fun Pair<Int, Int>.toSeatCoordinates(): Day11.SeatCoordinates = Day11.SeatCoordinates(this.first, this.second)
