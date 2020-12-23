package day23

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay23 {
    @Test
    fun testPart1() {
        assertThat(Day23("389125467").part1()).isEqualTo("67384529")
    }

    @Test
    fun part1() {
        assertThat(Day23("418976235").part1()).isEqualTo("96342875")
    }

    @Test
    fun testPart2() {
        assertThat(Day23("389125467").part2()).isEqualTo(149245887792)
    }

    @Test
    fun part2() {
        assertThat(Day23("418976235").part2()).isEqualTo(563362809504)
    }
}