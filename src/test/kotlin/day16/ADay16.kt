package day16

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay16 {

    @Test
    fun testPart1() {
        assertThat(Day16("inputFiles/day16/testinput.txt").part1()).isEqualTo(71)
    }

    @Test
    fun part1() {
        assertThat(Day16("inputFiles/day16/input.txt").part1()).isEqualTo(26988)
    }

    @Test
    fun testPart2() {
        assertThat(Day16("inputFiles/day16/input.txt").part2()).isEqualTo(426362917709L)
    }

}