package day7

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay7 {
    @Test
    fun testPart1() {
        assertThat(Day7("inputFiles/day7/testinput.txt").part1()).isEqualTo(4)
    }

    @Test
    fun testPart2() {
        assertThat(Day7("inputFiles/day7/testinput2.txt").part2()).isEqualTo(132)
    }

    @Test
    fun part1() {
        assertThat(Day7("inputFiles/day7/input.txt").part1()).isEqualTo(8742)
    }

//    @Test
//    fun part2() {
//        println("day7 part2: ${Day7("inputFiles/day7/input.txt").part2()}")
//    }
}