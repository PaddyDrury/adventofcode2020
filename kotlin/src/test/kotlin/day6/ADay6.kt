package day6

import org.assertj.core.api.Assertions
import org.junit.Test

class ADay6 {
    @Test
    fun testPart1() {
        Assertions.assertThat(Day6("inputFiles/day6/testinput.txt").part1()).isEqualTo(11)
    }

    @Test
    fun testPart2() {
        Assertions.assertThat(Day6("inputFiles/day6/testinput.txt").part2()).isEqualTo(6)
    }

    @Test
    fun part1() {
        println("day6 part1: ${Day6("inputFiles/day6/input.txt").part1()}")
    }

    @Test
    fun part2() {
        println("day6 part2: ${Day6("inputFiles/day6/input.txt").part2()}")
    }
}