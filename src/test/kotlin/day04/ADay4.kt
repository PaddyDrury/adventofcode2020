package day04

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay4 {
    @Test
    fun testPart1() {
        assertThat(Day4("inputFiles/day04/testinput.txt").part1()).isEqualTo(2)
    }

    @Test
    fun testPart2Invalid() {
        assertThat(Day4("inputFiles/day04/testinvalid.txt").part2()).isEqualTo(0)
    }

    @Test
    fun testPart2Valid() {
        assertThat(Day4("inputFiles/day04/testvalid.txt").part2()).isEqualTo(4)
    }

    @Test
    fun part1() {
        println("day04 part1: ${Day4("inputFiles/day04/input.txt").part1()}")
    }

    @Test
    fun part2() {
        println("day04 part2: ${Day4("inputFiles/day04/input.txt").part2()}")
    }
}