package day08

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay8 {

    @Test
    fun testParseInt() {
        assertThat("+2".toInt()).isEqualTo(2)
        assertThat("-20".toInt()).isEqualTo(-20)
    }

    @Test
    fun testPart1() {
        assertThat(Day8("inputFiles/day08/testinput.txt").part1()).isEqualTo(5)
    }

    @Test
    fun testPart2() {
        assertThat(Day8("inputFiles/day08/testinput.txt").part2()).isEqualTo(8)
    }

    @Test
    fun part1() {
        assertThat(Day8("inputFiles/day08/input.txt").part1()).isEqualTo(1337)
    }

    @Test
    fun part2() {
        assertThat(Day8("inputFiles/day08/input.txt").part2()).isEqualTo(1358)
    }
}