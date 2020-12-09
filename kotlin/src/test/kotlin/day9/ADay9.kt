package day9

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay9 {

    @Test
    fun testPart1() {
        assertThat(Day9("inputFiles/day9/testinput.txt").findFirstInvalid(5)).isEqualTo(127)
    }

//    @Test
//    fun testPart2() {
//        assertThat(Day9("inputFiles/day9/testinput.txt").part2()).isEqualTo(9)
//    }

    @Test
    fun part1() {
        assertThat(Day9("inputFiles/day9/input.txt").part1()).isEqualTo(41682220)
    }

//    @Test
//    fun part2() {
//        assertThat(Day9("inputFiles/day9/input.txt").part2()).isEqualTo(1359)
//    }
}