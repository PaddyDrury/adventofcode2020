package day12

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay12 {

    @Test
    fun testPart1() {
        assertThat(Day12("inputFiles/day12/testinput.txt").part1()).isEqualTo(25)
    }

    @Test
    fun part1() {
        assertThat(Day12("inputFiles/day12/input.txt").part1()).isEqualTo(998)
    }

    @Test
    fun testPart2() {
        assertThat(Day12("inputFiles/day12/testinput.txt").part2()).isEqualTo(286)
    }

    @Test
    fun part2() {
        assertThat(Day12("inputFiles/day12/input.txt").part2()).isEqualTo(71586)
    }

    @Test
    fun testRotate() {
        assertThat(Day12.Coords(10, 4).rotate(90)).isEqualTo(Day12.Coords(4, -10))
    }

}