package day03


import org.assertj.core.api.Assertions
import org.junit.Test

class ADay3 {
    @Test
    fun testPart1() {
        Assertions.assertThat(Day3("inputFiles/day03/testinput.txt").part1()).isEqualTo(7)
    }

    @Test
    fun testPart2() {
        Assertions.assertThat(Day3("inputFiles/day03/testinput.txt").part2()).isEqualTo(336)
    }

    @Test
    fun part1() {
        println("day03 part1: ${Day3("inputFiles/day03/input.txt").part1()}")
    }

    @Test
    fun part2() {
        println("day03 part2: ${Day3("inputFiles/day03/input.txt").part2()}")
    }
}