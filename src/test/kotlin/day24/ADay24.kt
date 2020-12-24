package day24

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay24 {
    @Test
    fun testPart1() {
        assertThat(Day24("inputFiles/day24/testinput.txt").part1()).isEqualTo(10)
    }

    @Test
    fun part1() {
        assertThat(Day24("inputFiles/day24/input.txt").part1()).isEqualTo(326)
    }

//    @Test
//    fun testPart2() {
//        assertThat(Day24("inputFiles/day24/testinput.txt").part2()).isEqualTo(291)
//    }
//
//    @Test
//    fun part2() {
//        assertThat(Day24("inputFiles/day24/input.txt").part2()).isEqualTo(31963)
//    }
}