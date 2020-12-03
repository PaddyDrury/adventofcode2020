package day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay1 {
    @Test
    fun testPart1() {
        assertThat(Day1("inputFiles/day1/testinput.txt").part1()).isEqualTo(514579)
    }

    @Test
    fun testPart2() {
        assertThat(Day1("inputFiles/day1/testinput.txt").part2()).isEqualTo(241861950)
    }

    @Test
    fun part1() {
        println("day1 part1: ${Day1("inputFiles/day1/input.txt").part1()}")
    }

    @Test
    fun part2() {
        println("day1 part2: ${Day1("inputFiles/day1/input.txt").part2()}")
    }
}