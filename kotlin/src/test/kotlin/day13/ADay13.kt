package day13

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay13 {

    @Test
    fun testPart1() {
        assertThat(Day13("inputFiles/day13/testinput.txt").part1()).isEqualTo(295)
    }

    @Test
    fun part1() {
        assertThat(Day13("inputFiles/day13/input.txt").part1()).isEqualTo(2545)
    }

    @Test
    fun testPart2() {
        assertThat(Day13("inputFiles/day13/testinput.txt").part2()).isEqualTo(1068781)
    }

    @Test
    fun part2() {
        assertThat(Day13("inputFiles/day13/input.txt").part2()).isEqualTo(266204454441577L)
    }
}