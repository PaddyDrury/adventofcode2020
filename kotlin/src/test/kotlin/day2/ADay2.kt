package day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay2 {
    @Test
    fun testPart1() {
        assertThat(Day2("inputFiles/day2/testinput.txt").part1()).isEqualTo(2)
    }

    @Test
    fun testPart2() {
        assertThat(Day2("inputFiles/day2/testinput.txt").part2()).isEqualTo(1)
    }

    @Test
    fun part1() {
        println("day2 part1: ${Day2("inputFiles/day2/input.txt").part1()}")
    }

    @Test
    fun part2() {
        println("day2 part2: ${Day2("inputFiles/day2/input.txt").part2()}")
    }
}