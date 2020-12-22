package day22

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay22 {

    @Test
    fun testPart1() {
        assertThat(Day22("inputFiles/day22/testinput.txt").part1()).isEqualTo(306)
    }

    @Test
    fun part1() {
        assertThat(Day22("inputFiles/day22/input.txt").part1()).isEqualTo(33393)
    }
}