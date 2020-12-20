package day19

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay19 {
    @Test
    fun testPart1() {
        assertThat(Day19("inputFiles/day19/testinput.txt").part1()).isEqualTo(2)
    }


    @Test
    fun part1() {
        assertThat(Day19("inputFiles/day19/input.txt").part1()).isEqualTo(173)
    }

    @Test
    fun part2() {
        assertThat(Day19("inputFiles/day19/input.txt").part2()).isEqualTo(367)
    }
}