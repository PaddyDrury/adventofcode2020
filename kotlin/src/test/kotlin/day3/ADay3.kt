package day3


import org.assertj.core.api.Assertions
import org.junit.Test

class ADay3 {
    @Test
    fun testPart1() {
        Assertions.assertThat(Day3("inputFiles/day3/testinput.txt").part1()).isEqualTo(7)
    }

    @Test
    fun testPart3() {
        Assertions.assertThat(Day3("inputFiles/day3/testinput.txt").part2()).isEqualTo(336)
    }

    @Test
    fun part1() {
        println("day3 part1: ${Day3("inputFiles/day3/input.txt").part1()}")
    }

    @Test
    fun part3() {
        println("day3 part3: ${Day3("inputFiles/day3/input.txt").part2()}")
    }
}