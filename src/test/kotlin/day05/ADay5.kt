package day05

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.RoundingMode

class ADay5 {

    val test = Day5("inputFiles/day05/testinput.txt")

    @Test
    fun testMidPoint() {
        assertThat(midPoint(0, 127, RoundingMode.FLOOR)).isEqualTo(63)
        assertThat(midPoint(44, 45, RoundingMode.CEILING)).isEqualTo(45)
    }

    @Test
    fun testSeatCodes() {
        assertThat(test.seatId("FBFBBFFRLR")).isEqualTo(357)
        assertThat(test.seatId("BFFFBBFRRR")).isEqualTo(567)
        assertThat(test.seatId("FFFBBBFRRR")).isEqualTo(119)
        assertThat(test.seatId("BBFFBBFRLL")).isEqualTo(820)
    }

    @Test
    fun testPart1() {
        assertThat(test.part1()).isEqualTo(820)
    }

    @Test
    fun part1() {
        println("day05 part1: ${Day5("inputFiles/day05/input.txt").part1()}")
    }

    @Test
    fun part2() {
        println("day05 part2: ${Day5("inputFiles/day05/input.txt").part2()}")
    }
}
