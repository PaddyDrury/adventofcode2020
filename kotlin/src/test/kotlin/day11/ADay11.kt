package day11

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay11 {

    @Test
    fun testGetAdjacentSeats() {
        assertThat(Day11.SeatCoordinates(3, 7).adjacentSeatCoordinates()).containsExactlyInAnyOrder(
                Day11.SeatCoordinates(2, 6),
                Day11.SeatCoordinates(2, 7),
                Day11.SeatCoordinates(2, 8),
                Day11.SeatCoordinates(3, 6),
                Day11.SeatCoordinates(3, 8),
                Day11.SeatCoordinates(4, 6),
                Day11.SeatCoordinates(4, 7),
                Day11.SeatCoordinates(4, 8),
        )
    }

    @Test
    fun testPart1() {
        assertThat(Day11("inputFiles/day11/testinput.txt").part1()).isEqualTo(37)
    }

    @Test
    fun testPart2() {
        assertThat(Day11("inputFiles/day11/testinput.txt").part2()).isEqualTo(26)
    }

    @Test
    fun part1() {
        assertThat(Day11("inputFiles/day11/input.txt").part1()).isEqualTo(2386)
    }

    @Test
    fun part2() {
        assertThat(Day11("inputFiles/day11/input.txt").part2()).isEqualTo(2091)
    }

}