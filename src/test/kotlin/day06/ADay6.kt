package day06

import org.assertj.core.api.Assertions
import org.junit.Test

class ADay6 {
    @Test
    fun testPart1() {
        Assertions.assertThat(Day6("inputFiles/day06/testinput.txt").part1()).isEqualTo(11)
    }

    @Test
    fun testPart2() {
        Assertions.assertThat(Day6("inputFiles/day06/testinput.txt").part2()).isEqualTo(6)
    }

    @Test
    fun part1() {
        println("day06 part1: ${Day6("inputFiles/day06/input.txt").part1()}")
    }

    @Test
    fun part2() {
        println("day06 part2: ${Day6("inputFiles/day06/input.txt").part2()}")
    }
}