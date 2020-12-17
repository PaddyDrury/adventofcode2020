package day17

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay17 {

    @Test
    fun testPart1() {
        assertThat(Day17("inputFiles/day17/testinput.txt").part1()).isEqualTo(112)
    }

    @Test
    fun part1() {
        assertThat(Day17("inputFiles/day17/input.txt").part1()).isEqualTo(211)
    }

    @Test
    fun testPart2() {
        assertThat(Day17("inputFiles/day17/testinput.txt").part2()).isEqualTo(848)
    }

    @Test
    fun part2() {
        assertThat(Day17("inputFiles/day17/input.txt").part2()).isEqualTo(1952)
    }

}