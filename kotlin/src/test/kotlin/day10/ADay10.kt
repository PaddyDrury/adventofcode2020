package day10

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay10 {
    @Test
    fun testPart1Input1() {
        assertThat(Day10("inputFiles/day10/testinput1.txt").findDifferencesOf(1)).isEqualTo(7)
        assertThat(Day10("inputFiles/day10/testinput1.txt").findDifferencesOf(3)).isEqualTo(5)
    }

    @Test
    fun testPart1Input2() {
        assertThat(Day10("inputFiles/day10/testinput2.txt").findDifferencesOf(1)).isEqualTo(22)
        assertThat(Day10("inputFiles/day10/testinput2.txt").findDifferencesOf(3)).isEqualTo(10)
    }

    @Test
    fun testPart2Input1() {
        assertThat(Day10("inputFiles/day10/testinput1.txt").part2()).isEqualTo(8)
    }

    @Test
    fun testPart2Input2() {
        assertThat(Day10("inputFiles/day10/testinput2.txt").part2()).isEqualTo(19208)
    }

    @Test
    fun part1() {
        assertThat(Day10("inputFiles/day10/input.txt").part1()).isEqualTo(2040)
    }

    @Test
    fun part2() {
        assertThat(Day10("inputFiles/day10/input.txt").part2()).isEqualTo(28346956187648)
    }
}